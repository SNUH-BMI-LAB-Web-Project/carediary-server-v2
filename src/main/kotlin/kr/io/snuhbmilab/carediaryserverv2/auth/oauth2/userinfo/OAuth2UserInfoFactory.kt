package kr.io.snuhbmilab.carediaryserverv2.auth.oauth2.userinfo

import kr.io.snuhbmilab.carediaryserverv2.auth.exception.OAuth2Exception

object OAuth2UserInfoFactory {
    fun create(provider: OAuth2Provider, attributes: Map<String, Any?>): OAuth2UserInfo = when (provider) {
        OAuth2Provider.GOOGLE -> GoogleOAuth2UserInfo(attributes)
        OAuth2Provider.KAKAO -> KakaoOAuth2UserInfo(attributes)
        OAuth2Provider.NAVER -> NaverOAuth2UserInfo(attributes)
        else -> throw OAuth2Exception("Unsupported OAuth2 provider: $provider")
    }
}