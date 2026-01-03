package kr.io.snuhbmilab.carediaryserverv2.common.logging

import io.github.oshai.kotlinlogging.KotlinLogging
import jakarta.servlet.FilterChain
import jakarta.servlet.ServletException
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.stereotype.Component
import org.springframework.web.filter.OncePerRequestFilter
import java.io.IOException

private val kotlinLogger = KotlinLogging.logger {}

@Component
class LoggingFilter : OncePerRequestFilter() {
    @Throws(ServletException::class, IOException::class)
    public override fun doFilterInternal(
        request: HttpServletRequest,
        response: HttpServletResponse,
        filterChain: FilterChain
    ) {
        val start = System.currentTimeMillis()

        filterChain.doFilter(request, response)

        val end = System.currentTimeMillis()
        val elapsed = end - start

        kotlinLogger.info { "[${request.method}] ${request.requestURI} (ip=${request.ip}) (status=${response.status}) ${elapsed}ms" }
    }

    private val HttpServletRequest.ip: String?
        get() {
            val xfHeader = getHeader("X-Forwarded-For")

            return if (xfHeader == null) remoteAddr else xfHeader.split(",".toRegex())
                .dropLastWhile { it.isEmpty() }.toTypedArray()[0]
        }
}