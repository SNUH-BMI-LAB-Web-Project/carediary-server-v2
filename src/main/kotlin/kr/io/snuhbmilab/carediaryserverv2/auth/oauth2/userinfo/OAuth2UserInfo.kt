package kr.io.snuhbmilab.carediaryserverv2.auth.oauth2.userinfo

interface OAuth2UserInfo {
    val attributes: Map<String, Any?>
    val email: String
    val name: String
    val provider: OAuth2Provider
    val socialId: String
}