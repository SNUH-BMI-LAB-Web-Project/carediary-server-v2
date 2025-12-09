package kr.io.snuhbmilab.carediaryserverv2.auth.jwt.service

import io.jsonwebtoken.Claims
import io.jsonwebtoken.ExpiredJwtException
import io.jsonwebtoken.Header
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.SignatureAlgorithm
import jakarta.xml.bind.DatatypeConverter
import kr.io.snuhbmilab.carediaryserverv2.auth.exception.AuthErrorCode
import kr.io.snuhbmilab.carediaryserverv2.auth.jwt.JwtProperties
import kr.io.snuhbmilab.carediaryserverv2.common.constants.Role
import kr.io.snuhbmilab.carediaryserverv2.common.exception.BusinessException
import kr.io.snuhbmilab.carediaryserverv2.domain.user.entity.User
import org.springframework.stereotype.Service
import java.security.Key
import java.time.Duration
import java.util.Date
import java.util.UUID
import javax.crypto.spec.SecretKeySpec

val signatureAlgorithm = SignatureAlgorithm.HS256

@Service
class JwtProvider(
    private val jwtProperties: JwtProperties
) {

    private fun createSecretKey(): Key {
        val apiKeySecretBytes = DatatypeConverter.parseBase64Binary(jwtProperties.secretKey)
        return SecretKeySpec(apiKeySecretBytes, signatureAlgorithm.jcaName)
    }

    fun generateToken(user: User, expiredAt: Duration): String {
        val now = Date()
        return makeToken(
            Date(now.time + expiredAt.toMillis()),
            user.email,
            mapOf(
                Pair("nickname", user.nickname),
                Pair("role", user.role.name),
            )
        )
    }

    private fun makeToken(expiry: Date, subject: String, claims: Map<String, Any>): String {
        val now = Date()
        val jwtBuilder = Jwts.builder()
            .setHeaderParam(Header.TYPE, Header.JWT_TYPE)
            .setIssuer(jwtProperties.issuer)
            .setIssuedAt(now)
            .setExpiration(expiry)
            .setSubject(subject)

        claims.forEach { (key, value) ->
            jwtBuilder.claim(key, value)
        }

        return jwtBuilder
            .signWith(createSecretKey(), signatureAlgorithm)
            .compact()
    }


    fun validToken(token: String?): Boolean {
        try {
            if (token == null) return false

            Jwts.parserBuilder()
                .setSigningKey(createSecretKey())
                .build()
                .parseClaimsJws(token)

            return true
        } catch (_: ExpiredJwtException) {
            throw BusinessException(AuthErrorCode.EXPIRED_ACCESS_TOKEN)
        } catch (_: Exception) {
            throw BusinessException(AuthErrorCode.INVALID_ACCESS_TOKEN)
        }
    }

    fun extractUserIdFromToken(token: String?): UUID? {
        if (token == null) return null

        return UUID.fromString(getClaims(token).subject)
    }

    fun extractUserRoleFromToken(token: String?): Role {
        if (token == null) return Role.USER

        val claims = getClaims(token)

        return Role.valueOf(claims["role"].toString())
    }

    fun getClaims(token: String): Claims {
        return Jwts.parserBuilder()
            .setSigningKey(createSecretKey())
            .build()
            .parseClaimsJws(token)
            .body
    }
}