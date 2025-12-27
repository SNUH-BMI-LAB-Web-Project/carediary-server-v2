package kr.io.snuhbmilab.carediaryserverv2.auth.oauth2.handler

import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import kr.io.snuhbmilab.carediaryserverv2.auth.exception.OAuth2Exception
import kr.io.snuhbmilab.carediaryserverv2.auth.jwt.service.JwtProvider
import kr.io.snuhbmilab.carediaryserverv2.auth.oauth2.OAuth2Authentication
import kr.io.snuhbmilab.carediaryserverv2.domain.scalequestion.service.ScaleQuestionService
import kr.io.snuhbmilab.carediaryserverv2.domain.user.service.UserService
import org.springframework.beans.factory.annotation.Value
import org.springframework.security.core.Authentication
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler
import org.springframework.stereotype.Component
import org.springframework.web.util.UriComponentsBuilder

@Component
class OAuth2AuthenticationSuccessHandler(
    private val jwtProvider: JwtProvider,

    @Value($$"${service.oauth2.redirect-uri}")
    private var oAuth2RedirectUri: String = "",

    private val userService: UserService,

    private val scaleQuestionService: ScaleQuestionService
) : SimpleUrlAuthenticationSuccessHandler() {

    override fun onAuthenticationSuccess(
        request: HttpServletRequest,
        response: HttpServletResponse,
        authentication: Authentication
    ) {
        validateAuthenticationPrincipal(authentication)

        val oAuth2Authentication = authentication.principal as OAuth2Authentication
        val oAuth2UserInfo = oAuth2Authentication.oAuth2UserInfo
        val socialId = oAuth2UserInfo.socialId
        val provider = oAuth2UserInfo.provider
        val email = oAuth2UserInfo.email

        val findByEmail = userService.findByEmail(email)

        if (findByEmail != null && findByEmail.socialProviderId.socialProvider != provider.registrationId) {
            redirectStrategy.sendRedirect(
                request,
                response,
                redirectUri(oAuth2RedirectUri, CallbackType.DUPLICATE_EMAIL)
            )
            return
        }

        val user = userService.findOrCreate(email, provider.registrationId, socialId)

        if (user.isRegistered() && !user.isAdmin() && scaleQuestionService.needsScaleQuestion(user, 0)) {
            redirectStrategy.sendRedirect(
                request,
                response,
                redirectUri(oAuth2RedirectUri, CallbackType.NEED_SCALE_QUESTION)
            )
            return
        }

        val callbackType = if (!user.isRegistered()) CallbackType.NEW else CallbackType.SUCCESS
        val accessToken = jwtProvider.generateToken(user)

        redirectStrategy.sendRedirect(request, response, redirectUri(oAuth2RedirectUri, callbackType, accessToken))
    }

    private fun validateAuthenticationPrincipal(authentication: Authentication) {
        if (authentication.principal !is OAuth2Authentication) throw OAuth2Exception("Invalid OAuth2 Authentication Principal")
    }

    private fun redirectUri(
        targetUrl: String,
        type: CallbackType,
        token: String? = null
    ): String {
        val uriBuilder = UriComponentsBuilder.fromUriString(targetUrl)
            .queryParam("type", type)

        if (token != null) {
            uriBuilder.queryParam("token", token)
        }

        return uriBuilder.build().toUriString()
    }

    enum class CallbackType {
        SUCCESS, DUPLICATE_EMAIL, NEW, NEED_SCALE_QUESTION
    }
}