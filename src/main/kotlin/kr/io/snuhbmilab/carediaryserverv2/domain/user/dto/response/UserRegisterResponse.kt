package kr.io.snuhbmilab.carediaryserverv2.domain.user.dto.response

data class UserRegisterResponse(
    val accessToken: String
) {
    companion object {
        @JvmStatic
        fun from(accessToken: String) = UserRegisterResponse(accessToken)
    }
}