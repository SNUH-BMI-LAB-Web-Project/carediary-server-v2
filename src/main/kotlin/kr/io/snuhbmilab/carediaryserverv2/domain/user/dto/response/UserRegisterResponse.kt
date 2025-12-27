
package kr.io.snuhbmilab.carediaryserverv2.domain.user.dto.response

import io.swagger.v3.oas.annotations.media.Schema

@Schema(description = "회원가입 응답")
data class UserRegisterResponse(
    @Schema(description = "새로 발급된 JWT 액세스 토큰", example = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...")
    val accessToken: String
) {
    companion object {
        @JvmStatic
        fun from(accessToken: String) = UserRegisterResponse(accessToken)
    }
}