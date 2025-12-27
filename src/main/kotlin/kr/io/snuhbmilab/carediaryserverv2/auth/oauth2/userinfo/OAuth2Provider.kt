package kr.io.snuhbmilab.carediaryserverv2.auth.oauth2.userinfo

enum class OAuth2Provider {
    UNKNOWN, GOOGLE, KAKAO, NAVER;

    val registrationId: String = name.lowercase()

    companion object {
        fun from(registrationId: String): OAuth2Provider =
            entries.firstOrNull { it.registrationId == registrationId } ?: UNKNOWN
    }
}