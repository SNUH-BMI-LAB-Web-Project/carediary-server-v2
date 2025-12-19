package kr.io.snuhbmilab.carediaryserverv2.auth.jwt.filter

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.registerKotlinModule
import jakarta.servlet.FilterChain
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import kr.io.snuhbmilab.carediaryserverv2.auth.exception.AuthErrorCode
import kr.io.snuhbmilab.carediaryserverv2.auth.jwt.service.JwtProvider
import kr.io.snuhbmilab.carediaryserverv2.auth.security.UserAuthentication
import kr.io.snuhbmilab.carediaryserverv2.common.ErrorCode
import kr.io.snuhbmilab.carediaryserverv2.common.constants.WHITELIST_ENDPOINTS
import kr.io.snuhbmilab.carediaryserverv2.common.dto.CommonResponse
import kr.io.snuhbmilab.carediaryserverv2.common.exception.BusinessException
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Component
import org.springframework.web.filter.OncePerRequestFilter
import java.io.IOException

const val HEADER_AUTHORIZATION = "Authorization"
const val HEADER_BEARER = "Bearer"

@Component
class JwtAuthenticationFilter(
    private val jwtProvider: JwtProvider
) : OncePerRequestFilter() {

    override fun doFilterInternal(
        request: HttpServletRequest,
        response: HttpServletResponse,
        filterChain: FilterChain
    ) {
        val authorizationHeader = request.getHeader(HEADER_AUTHORIZATION)

        authorizationHeader?.let {
            val accessToken = getAccessToken(authorizationHeader)

            runCatching {
                if (jwtProvider.validToken(accessToken)) {
                    val userId = jwtProvider.extractUserIdFromToken(accessToken)
                        ?: throw BusinessException(AuthErrorCode.INVALID_ACCESS_TOKEN)

                    val userRole = jwtProvider.extractUserRoleFromToken(accessToken)

                    SecurityContextHolder.getContext().authentication = UserAuthentication(userId, userRole)
                }
            }.onFailure { exception ->
                if (exception !is BusinessException) return

                sendErrorResponse(response, exception.errorCode)
                return
            }
        }

        filterChain.doFilter(request, response)
    }

    override fun shouldNotFilter(request: HttpServletRequest): Boolean {
        val excludes = WHITELIST_ENDPOINTS
        val path = request.requestURI
        return excludes.any { path.startsWith(it) }
    }

    @Throws(IOException::class)
    private fun sendErrorResponse(response: HttpServletResponse, errorCode: ErrorCode) {
        val objectMapper = ObjectMapper().registerKotlinModule()

        response.apply {
            addHeader("Content-Type", "application/json; charset=UTF-8")
            status = errorCode.httpStatus.value()

            with(writer) {
                write(objectMapper.writeValueAsString(CommonResponse.error(errorCode)))
                flush()
            }
        }
    }

    private fun getAccessToken(authorizationHeader: String): String {
        if (authorizationHeader.startsWith(HEADER_BEARER)) {
            return authorizationHeader.substring(HEADER_BEARER.length)
        }

        throw BusinessException(AuthErrorCode.INVALID_ACCESS_TOKEN)
    }
}