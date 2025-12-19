package kr.io.snuhbmilab.carediaryserverv2.domain.user.controller

import kr.io.snuhbmilab.carediaryserverv2.common.annotation.UserId
import kr.io.snuhbmilab.carediaryserverv2.common.dto.CommonResponse
import kr.io.snuhbmilab.carediaryserverv2.domain.user.dto.request.UserRegisterRequest
import kr.io.snuhbmilab.carediaryserverv2.domain.user.dto.response.CurrentUserResponse
import kr.io.snuhbmilab.carediaryserverv2.domain.user.facade.UserFacade
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.util.UUID

@RestController
@RequestMapping("/v1/users")
class UserController(
    private val userFacade: UserFacade
) {

    @PostMapping("/register")
    fun register(@UserId userId: UUID, @RequestBody request: UserRegisterRequest): CommonResponse<Unit> {
        userFacade.register(userId, request)
        return CommonResponse.ok()
    }

    @GetMapping("/me")
    fun getMe(@UserId userId: UUID): CommonResponse<CurrentUserResponse> {
        return CommonResponse.ok(userFacade.getMe(userId))
    }
}