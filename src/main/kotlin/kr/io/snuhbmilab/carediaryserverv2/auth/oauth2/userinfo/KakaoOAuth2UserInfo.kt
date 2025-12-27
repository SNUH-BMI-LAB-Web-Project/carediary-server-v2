package kr.io.snuhbmilab.carediaryserverv2.auth.oauth2.userinfo

import kr.io.snuhbmilab.carediaryserverv2.auth.exception.OAuth2Exception


class KakaoOAuth2UserInfo(
    override val attributes: Map<String, Any?>
) : OAuth2UserInfo {
    private val kakaoAccount = attributes["kakao_account"] as Map<String, String?>
    private val properties = attributes["properties"] as Map<String, String?>

    override val email: String = kakaoAccount["email"] ?:
        throw OAuth2Exception("The kakao account doesn't have an email address.")

    override val name: String = properties["nickname"] ?:
        throw OAuth2Exception("The kakao account doesn't have a nickname.")

    override val provider: OAuth2Provider = OAuth2Provider.KAKAO

    override val socialId: String = attributes["id"]?.toString() ?:
        throw OAuth2Exception("The kakao account doesn't have an id.")
}