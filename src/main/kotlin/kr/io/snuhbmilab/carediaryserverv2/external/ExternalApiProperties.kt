package kr.io.snuhbmilab.carediaryserverv2.external

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.stereotype.Component

@Component
@ConfigurationProperties(prefix = "external-api")
data class ExternalApiProperties(
    var modelUri: String = ""
)