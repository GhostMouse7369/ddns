package top.laoshuzi.ddns.service.impl

import okhttp3.OkHttpClient
import okhttp3.Request
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import top.laoshuzi.ddns.properties.DdnsProperties
import top.laoshuzi.ddns.service.IpService
import java.util.regex.Pattern

/**
 * Created by mouse on 2019/6/9.
 */
@Service
class GmIpService : IpService {

    @Autowired
    var ddnsProperties: DdnsProperties = DdnsProperties()

    var okClient: OkHttpClient = OkHttpClient()

    @Throws(Exception::class)
    override fun getLocalOutIp(): String? {

        var ip: String? = null

        val request = Request.Builder()
                .url(ddnsProperties.config.ipUrl)
                .build()

        val response = okClient.newCall(request)?.execute() ?: throw Exception("get ip address request error")

        val p = Pattern.compile("((?!\\d\\d\\d)\\d+|1\\d\\d|2[0-4]\\d|25[0-5])\\.((?!\\d\\d\\d)\\d+|1\\d\\d|2[0-4]\\d|25[0-5])\\.((?!\\d\\d\\d)\\d+|1\\d\\d|2[0-4]\\d|25[0-5])\\.((?!\\d\\d\\d)\\d+|1\\d\\d|2[0-4]\\d|25[0-5])")
        val m = p.matcher(response.body()!!.string())
        if (m.find()) {
            ip = m.group(0)
        }

        println("获取:[IP=$ip]")

        return ip
    }
}
