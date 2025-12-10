package kr.io.snuhbmilab.carediaryserverv2.common.config

import kr.io.snuhbmilab.carediaryserverv2.auth.jwt.filter.JwtAuthenticationFilter
import kr.io.snuhbmilab.carediaryserverv2.common.constants.Role
import kr.io.snuhbmilab.carediaryserverv2.common.properties.CorsProperties
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.Customizer.withDefaults
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.web.SecurityFilterChain
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter
import org.springframework.web.cors.CorsConfiguration
import org.springframework.web.cors.CorsConfigurationSource
import org.springframework.web.cors.UrlBasedCorsConfigurationSource

val WHITELIST_ENDPOINTS = arrayOf(
    "/auth/**",
    "/v3/api-docs/**",
    "/swagger-ui/**",
    "/swagger-resources/**",
    "/swagger-ui.html",
)

const val ADMIN_ENDPOINT = "/admin/**"

@Configuration
@EnableWebSecurity
@EnableMethodSecurity(prePostEnabled = true)
class SecurityConfig(
    private val jwtAuthenticationFilter: JwtAuthenticationFilter,
    private val corsProperties: CorsProperties,
) {
    @Bean
    fun securityFilterChain(httpSecurity: HttpSecurity): SecurityFilterChain {
        httpSecurity
            //Spring Security OAuth2.0 로그인 설정 추가
            .cors(withDefaults())
            .csrf { it.disable() }
            .formLogin { it.disable() }
            .httpBasic { it.disable() }
            .sessionManagement { it.sessionCreationPolicy(SessionCreationPolicy.STATELESS) }
            .authorizeHttpRequests {
                it.requestMatchers(*WHITELIST_ENDPOINTS).permitAll()
                    .requestMatchers(ADMIN_ENDPOINT).hasAnyRole(Role.ADMIN.name)
                    .anyRequest().authenticated()
            }
            .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter::class.java)

        return httpSecurity.build()
    }

    @Bean
    fun corsConfigurationSource(): CorsConfigurationSource {
        val configuration = CorsConfiguration().apply {
            allowedOrigins = corsProperties.allowedOrigins.toList()
            allowedMethods = listOf("GET", "POST", "PUT", "PATCH", "DELETE", "OPTIONS", "HEAD")
            allowedHeaders = listOf("Content-Type", "Authorization")
        }

        return UrlBasedCorsConfigurationSource().apply {
            registerCorsConfiguration("/**", configuration)
        }
    }
}