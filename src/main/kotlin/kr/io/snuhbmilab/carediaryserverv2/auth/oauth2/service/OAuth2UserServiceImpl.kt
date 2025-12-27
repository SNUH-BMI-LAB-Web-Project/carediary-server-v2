package kr.io.snuhbmilab.carediaryserverv2.auth.oauth2.service

import kr.io.snuhbmilab.carediaryserverv2.auth.exception.OAuth2Exception
import kr.io.snuhbmilab.carediaryserverv2.auth.oauth2.OAuth2Authentication
import kr.io.snuhbmilab.carediaryserverv2.auth.oauth2.userinfo.OAuth2Provider
import kr.io.snuhbmilab.carediaryserverv2.auth.oauth2.userinfo.OAuth2UserInfoFactory
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest
import org.springframework.security.oauth2.core.user.OAuth2User
import org.springframework.stereotype.Service

@Service
class OAuth2UserServiceImpl : DefaultOAuth2UserService() {

    override fun loadUser(userRequest: OAuth2UserRequest?): OAuth2User {
        if (userRequest == null) throw OAuth2Exception("userRequest must not be null")

        val oAuth2User = super.loadUser(userRequest)
        val registrationId = userRequest.clientRegistration.registrationId
        val attributes = oAuth2User.attributes
        val oAuth2UserInfo = OAuth2UserInfoFactory.create(OAuth2Provider.from(registrationId), attributes)

        return OAuth2Authentication(oAuth2UserInfo)
    }
}