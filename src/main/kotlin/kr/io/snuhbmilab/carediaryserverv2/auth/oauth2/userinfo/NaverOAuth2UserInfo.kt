package kr.io.snuhbmilab.carediaryserverv2.auth.oauth2.userinfo

import kr.io.snuhbmilab.carediaryserverv2.auth.exception.OAuth2Exception

class NaverOAuth2UserInfo(
    override val attributes: Map<String, Any?>
) : OAuth2UserInfo {
    private val response = attributes["response"] as Map<String, String?>

    override val email = response["email"] ?:
        throw OAuth2Exception("The naver account doesn't have an email address.")

    override val name = response["name"] ?:
        throw OAuth2Exception("The naver account doesn't have a name.")

    override val provider: OAuth2Provider = OAuth2Provider.NAVER

    override val socialId: String = response["id"] ?:
        throw OAuth2Exception("The naver account doesn't have an id.")
}