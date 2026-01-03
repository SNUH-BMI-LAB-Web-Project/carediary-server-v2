package kr.io.snuhbmilab.carediaryserverv2.admin.controller

import kr.io.snuhbmilab.carediaryserverv2.admin.dto.response.AdminUsageResponse
import kr.io.snuhbmilab.carediaryserverv2.admin.facade.AdminUsageFacade
import kr.io.snuhbmilab.carediaryserverv2.common.dto.CommonResponse
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/v1/admin/usage")
class AdminUsageController(
    private val adminUsageFacade: AdminUsageFacade
) : AdminUsageApi {

    @GetMapping
    override fun getUsage(): CommonResponse<AdminUsageResponse> {
        return CommonResponse.ok(adminUsageFacade.getUsage())
    }
}