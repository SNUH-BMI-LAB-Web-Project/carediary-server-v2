package kr.io.snuhbmilab.carediaryserverv2.auth.oauth2

import kr.io.snuhbmilab.carediaryserverv2.auth.oauth2.userinfo.OAuth2UserInfo
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.oauth2.core.user.OAuth2User

class OAuth2Authentication(
    val oAuth2UserInfo: OAuth2UserInfo
) : OAuth2User {
    override fun getAttributes(): Map<String, Any?> = oAuth2UserInfo.attributes

    override fun getAuthorities(): Collection<GrantedAuthority> = emptyList()

    override fun getName(): String = oAuth2UserInfo.name
}