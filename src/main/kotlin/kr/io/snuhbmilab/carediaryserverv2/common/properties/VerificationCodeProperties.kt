package kr.io.snuhbmilab.carediaryserverv2.common.properties

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.stereotype.Component

@Component
@ConfigurationProperties(prefix = "verification-code")
data class VerificationCodeProperties(
    var admin: String = "",
    var careManager: String = "",
)
