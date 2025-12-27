package kr.io.snuhbmilab.carediaryserverv2.auth.jwt

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.stereotype.Component
import java.time.Duration

@Component
@ConfigurationProperties(prefix = "jwt")
data class JwtProperties(
    var issuer: String = "",
    var secretKey: String = "",
    var expiresIn: Duration = Duration.ofDays(30)
) {
}