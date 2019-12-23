package top.laoshuzi.ddns.runner

import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.CommandLineRunner
import org.springframework.stereotype.Component
import org.springframework.util.StringUtils
import top.laoshuzi.ddns.dto.AddRecordDTO
import top.laoshuzi.ddns.dto.UpdateRecordDTO
import top.laoshuzi.ddns.properties.DdnsProperties
import top.laoshuzi.ddns.service.DomainRecordsService
import top.laoshuzi.ddns.service.IpService
import top.laoshuzi.ddns.service.impl.GmDomainRecordsService
import top.laoshuzi.ddns.service.impl.GmIpService
import java.text.SimpleDateFormat
import java.util.*

/**
 * Created by mouse on 2019/6/9.
 */
@Component
class DdnsRunner : CommandLineRunner {

    private val logger = LoggerFactory.getLogger(this.javaClass)

    @Autowired
    var ddnsProperties: DdnsProperties = DdnsProperties()

    @Autowired
    var domainRecordsService: DomainRecordsService = GmDomainRecordsService()

    @Autowired
    var ipService: IpService = GmIpService()

    override fun run(vararg args: String?) {
        RunnerThread().start()
    }

    inner class RunnerThread : Thread() {

        override fun run() {


            //创建协程
            val launch = GlobalScope.launch {

                delay(1000)
                while (true) {
                    logger.info("==============[${getNowTime()}]==============")

                    // 获取Ip
                    var ip: String? = null

                    logger.info("来自[${ddnsProperties.config.ipUrl}]")
                    for (i in 1..3) {
                        if (!StringUtils.isEmpty(ip))
                            break
                        logger.info("获取IP地址:第${i}次")
                        ip = ipService.getLocalOutIp()
                    }

                    if (StringUtils.isEmpty(ip))
                        throw Exception("无法获取IP地址")

                    // 遍历配置
                    ddnsProperties.domain.forEach { domainProperties ->
                        val domain = domainProperties.value
                        // 获取A记录
                        val records = domainRecordsService.getDomainRecordsByType(domain.domainName, "A")
                                .associateBy {
                                    // 转化成Map
                                    it.rR
                                }
                        // 遍历解析记录
                        domain.rrs.forEach { rr ->
                            logger.info("同步解析记录-->$rr.${domain.domainName}")
                            val record = records[rr]
                            if (record == null) {
                                // 添加记录
                                val addRecordDTO = AddRecordDTO().apply {
                                    domainName = domain.domainName
                                    type = "A"
                                    rR = rr
                                    value = ip
                                }
                                logger.info("添加解析记录-->${addRecordDTO.toString()}")
                                domainRecordsService.addDomainRecord(addRecordDTO)
                            } else {
                                // 更新记录
                                if (record.value == ip) {
                                    logger.info("IP地址无需变更")
                                } else {
                                    val updateRecordDTO = UpdateRecordDTO().apply {
                                        recordId = record.recordId
                                        type = record.type
                                        rR = record.rR
                                        value = ip
                                    }
                                    logger.info("更新解析记录-->${updateRecordDTO.toString()}")
                                    domainRecordsService.updateDomainRecord(updateRecordDTO)
                                }
                            }
                        }

                    }

                    logger.info("=================================================")
                    delay(300000)
                }

            }

            // 挂起
            runBlocking {
                logger.info("ddns service is starting...")
                launch.join()
            }

        }

    }

    fun getNowTime(): String {
        return SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(Date())
    }

}

