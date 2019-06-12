package top.laoshuzi.ddns.service.impl

import com.aliyuncs.AcsRequest
import com.aliyuncs.DefaultAcsClient
import com.aliyuncs.IAcsClient
import com.aliyuncs.alidns.model.v20150109.AddDomainRecordRequest
import com.aliyuncs.alidns.model.v20150109.DeleteDomainRecordRequest
import com.aliyuncs.alidns.model.v20150109.DescribeDomainRecordsRequest
import com.aliyuncs.alidns.model.v20150109.UpdateDomainRecordRequest
import com.aliyuncs.http.FormatType
import com.aliyuncs.http.MethodType
import com.aliyuncs.http.ProtocolType
import com.aliyuncs.profile.DefaultProfile
import org.springframework.beans.BeanUtils
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import top.laoshuzi.ddns.dto.AddRecordDTO
import top.laoshuzi.ddns.dto.DeleteRecordDTO
import top.laoshuzi.ddns.dto.RecordDTO
import top.laoshuzi.ddns.dto.UpdateRecordDTO
import top.laoshuzi.ddns.properties.DdnsProperties
import top.laoshuzi.ddns.service.DomainRecordsService
import javax.annotation.PostConstruct

/**
 * Created by mouse on 2019/6/7.
 */
@Service
class GmDomainRecordsService : DomainRecordsService {

    @Autowired
    var ddnsProperties: DdnsProperties = DdnsProperties()

    var acsClient: IAcsClient? = null

    @PostConstruct
    @Throws(Exception::class)
    fun start() {
        DefaultProfile.addEndpoint(
                ddnsProperties.config.regionId,
                ddnsProperties.config.regionId,
                "Alidns",
                "alidns.aliyuncs.com"
        )

        val profile = DefaultProfile.getProfile(
                ddnsProperties.config.regionId,
                ddnsProperties.config.accessKeyId,
                ddnsProperties.config.accessKeySecret
        )
        acsClient = DefaultAcsClient(profile)
    }

    @Throws(Exception::class)
    override fun getDomainRecords(domain: String): List<RecordDTO> {
        val request = DescribeDomainRecordsRequest().apply {
            domainName = domain
        }
        unifiedRequestConfig(request)

        val response = acsClient?.getAcsResponse(request)

        return response?.domainRecords?.map {
            RecordDTO().apply {
                BeanUtils.copyProperties(it, this)
            }
        }!!

    }

    @Throws(Exception::class)
    override fun getDomainRecordsByType(domain: String, type: String): List<RecordDTO> {
        return getDomainRecords(domain).filter {
            it.type.equals(type)
        }
    }

    @Throws(Exception::class)
    override fun addDomainRecord(addRecord: AddRecordDTO) {
        val request = AddDomainRecordRequest().apply {
            domainName = addRecord.domainName
            type = addRecord.type
            rr = addRecord.rR
            value = addRecord.value
        }
        unifiedRequestConfig(request)

        acsClient?.getAcsResponse(request)
    }

    @Throws(Exception::class)
    override fun deleteDomainRecord(deleteRecord: DeleteRecordDTO) {
        val request = DeleteDomainRecordRequest().apply {
            recordId = deleteRecord.recordId
        }
        unifiedRequestConfig(request)

        acsClient?.getAcsResponse(request)
    }

    @Throws(Exception::class)
    override fun updateDomainRecord(updateRecord: UpdateRecordDTO) {
        val request = UpdateDomainRecordRequest().apply {
            recordId = updateRecord.recordId
            type = updateRecord.type
            rr = updateRecord.rR
            value = updateRecord.value
        }
        unifiedRequestConfig(request)

        acsClient?.getAcsResponse(request)
    }

    fun <T : AcsRequest<*>> unifiedRequestConfig(req: T): T {
        return req.apply {
            protocol = ProtocolType.HTTPS
            acceptFormat = FormatType.JSON
            method = MethodType.POST
        }
    }

}
