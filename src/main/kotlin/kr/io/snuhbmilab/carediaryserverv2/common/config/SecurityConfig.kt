package kr.io.snuhbmilab.carediaryserverv2.common.config

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.registerKotlinModule
import kr.io.snuhbmilab.carediaryserverv2.auth.jwt.filter.JwtAuthenticationFilter
import kr.io.snuhbmilab.carediaryserverv2.auth.oauth2.handler.OAuth2AuthenticationFailureHandler
import kr.io.snuhbmilab.carediaryserverv2.auth.oauth2.handler.OAuth2AuthenticationSuccessHandler
import kr.io.snuhbmilab.carediaryserverv2.auth.oauth2.service.OAuth2UserServiceImpl
import kr.io.snuhbmilab.carediaryserverv2.common.GlobalErrorCode
import kr.io.snuhbmilab.carediaryserverv2.common.constants.ADMIN_ENDPOINT
import kr.io.snuhbmilab.carediaryserverv2.common.constants.Role
import kr.io.snuhbmilab.carediaryserverv2.common.constants.WHITELIST_ENDPOINTS
import kr.io.snuhbmilab.carediaryserverv2.common.dto.CommonResponse
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

@Configuration
@EnableWebSecurity
@EnableMethodSecurity(prePostEnabled = true)
class SecurityConfig(
    private val jwtAuthenticationFilter: JwtAuthenticationFilter,
    private val corsProperties: CorsProperties,
    private val oAuth2UserServiceImpl: OAuth2UserServiceImpl,
    private val oAuth2AuthenticationSuccessHandler: OAuth2AuthenticationSuccessHandler,
    private val oAuth2AuthenticationFailureHandler: OAuth2AuthenticationFailureHandler
) {
    @Bean
    fun securityFilterChain(httpSecurity: HttpSecurity): SecurityFilterChain {
        httpSecurity
            //Spring Security OAuth2.0 로그인 설정 추가
            .oauth2Login {
                it.authorizationEndpoint { authEndpoint -> authEndpoint.baseUri("/oauth2/authorization") }
                    .redirectionEndpoint { redirectionEndpoint -> redirectionEndpoint.baseUri("/*/oauth2/code/*") }
                    .userInfoEndpoint { userInfo -> userInfo.userService(oAuth2UserServiceImpl) }
                    .successHandler(oAuth2AuthenticationSuccessHandler)
                    .failureHandler(oAuth2AuthenticationFailureHandler)
            }
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
            .exceptionHandling {
                it.authenticationEntryPoint { _, response, _ ->
                    val objectMapper = ObjectMapper().registerKotlinModule()

                    response.addHeader("Content-Type", "application/json; charset=UTF-8")
                    response.status = GlobalErrorCode.FORBIDDEN.httpStatus.value()
                    response.writer.write(
                        objectMapper.writeValueAsString(CommonResponse.error(GlobalErrorCode.FORBIDDEN))
                    )
                }
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