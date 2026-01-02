package kr.io.snuhbmilab.carediaryserverv2.common.constants

val WHITELIST_ENDPOINTS = arrayOf(
    "/v1/auth/**",
    "/v3/api-docs/**",
    "/swagger-ui/**",
    "/swagger-resources/**",
    "/swagger-ui.html",
    "/v1/users/test/token"
)

const val ADMIN_ENDPOINT = "/**/admin/**"