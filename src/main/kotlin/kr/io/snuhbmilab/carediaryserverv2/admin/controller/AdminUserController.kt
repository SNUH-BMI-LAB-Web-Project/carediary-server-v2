package kr.io.snuhbmilab.carediaryserverv2.admin.controller

import kr.io.snuhbmilab.carediaryserverv2.admin.dto.response.AdminUserDetailResponse
import kr.io.snuhbmilab.carediaryserverv2.admin.dto.response.AdminUserFindAllResponse
import kr.io.snuhbmilab.carediaryserverv2.admin.dto.response.AdminUserScaleFindAllResponse
import kr.io.snuhbmilab.carediaryserverv2.admin.dto.response.AdminUserScaleQuestionResultResponse
import kr.io.snuhbmilab.carediaryserverv2.admin.facade.AdminUserFacade
import kr.io.snuhbmilab.carediaryserverv2.common.dto.CommonResponse
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import java.util.UUID

@RestController
@RequestMapping("/v1/admin/users")
class AdminUserController(
    private val adminUserFacade: AdminUserFacade
) {
    @GetMapping
    fun findAllUsers(): CommonResponse<AdminUserFindAllResponse> {
        return CommonResponse.ok(adminUserFacade.findAllUsers())
    }

    @GetMapping("/{userId}")
    fun findUserById(@PathVariable userId: UUID): CommonResponse<AdminUserDetailResponse> {
        return CommonResponse.ok(adminUserFacade.findUserById(userId))
    }

    @GetMapping("/{userId}/scales")
    fun findUserScales(@PathVariable userId: UUID): CommonResponse<AdminUserScaleFindAllResponse> {
        return CommonResponse.ok(adminUserFacade.findUserScales(userId))
    }

    @GetMapping("/{userId}/scale-questions")
    fun findScaleQuestionResult(@PathVariable userId: UUID, @RequestParam count: Int): CommonResponse<AdminUserScaleQuestionResultResponse> {
        return CommonResponse.ok(adminUserFacade.findScaleQuestionResult(userId, count))
    }
}