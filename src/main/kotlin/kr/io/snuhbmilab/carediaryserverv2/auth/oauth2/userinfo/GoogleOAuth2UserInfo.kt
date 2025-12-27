package kr.io.snuhbmilab.carediaryserverv2.auth.oauth2.userinfo

import kr.io.snuhbmilab.carediaryserverv2.auth.exception.OAuth2Exception

class GoogleOAuth2UserInfo(
    override val attributes: Map<String, Any?>
) : OAuth2UserInfo {
    override val email = attributes["email"] as String? ?:
        throw OAuth2Exception("The google account doesn't have an email address.")

    override val name = attributes["name"] as String? ?:
        throw OAuth2Exception("The google account doesn't have a name.")

    override val provider: OAuth2Provider = OAuth2Provider.GOOGLE

    override val socialId: String = attributes["sub"] as String? ?:
        throw OAuth2Exception("The google account doesn't have an id.")
}