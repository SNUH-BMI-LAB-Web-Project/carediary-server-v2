package kr.io.snuhbmilab.carediaryserverv2.admin.controller

import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.responses.ApiResponses
import io.swagger.v3.oas.annotations.security.SecurityRequirement
import io.swagger.v3.oas.annotations.tags.Tag
import kr.io.snuhbmilab.carediaryserverv2.admin.dto.response.AdminUsageResponse
import kr.io.snuhbmilab.carediaryserverv2.common.dto.CommonResponse

@Tag(name = "(Admin) Usage", description = "관리자 사용량 조회 API")
@SecurityRequirement(name = "JWT")
interface AdminUsageApi {

    @Operation(
        summary = "사용량 조회",
        description = "누적 사용량과 월간 사용량을 조회합니다. 사용자 수, 분석 건 수, 사용료를 포함합니다."
    )
    @ApiResponses(
        value = [
            ApiResponse(responseCode = "200", description = "사용량 조회 성공"),
            ApiResponse(responseCode = "401", description = "인증 실패"),
            ApiResponse(responseCode = "403", description = "권한 없음")
        ]
    )
    fun getUsage(): CommonResponse<AdminUsageResponse>
}