package kr.io.snuhbmilab.carediaryserverv2.common.properties

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.stereotype.Component

@Component
@ConfigurationProperties(prefix = "cors")
data class CorsProperties(
    var allowedOrigins: Array<String> = arrayOf(),
)