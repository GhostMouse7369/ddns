package top.laoshuzi.ddns.properties

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.stereotype.Component

/**
 * Created by mouse on 2019/6/7.
 */

@Component
@ConfigurationProperties(prefix = "top.laoshuzi.ddns")
class DdnsProperties {
    var config: ConfigProperties = ConfigProperties()
    var domain: Map<String, DomainProperties> = HashMap()
}
