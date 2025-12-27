package kr.io.snuhbmilab.carediaryserverv2.domain.home.controller

import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.Parameter
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.responses.ApiResponses
import io.swagger.v3.oas.annotations.security.SecurityRequirement
import io.swagger.v3.oas.annotations.tags.Tag
import kr.io.snuhbmilab.carediaryserverv2.common.annotation.UserId
import kr.io.snuhbmilab.carediaryserverv2.common.dto.CommonResponse
import kr.io.snuhbmilab.carediaryserverv2.domain.home.dto.response.HomeResponse
import java.util.UUID

@Tag(name = "Home", description = "홈 화면 API")
@SecurityRequirement(name = "JWT")
interface HomeApi {

    @Operation(
        summary = "홈 화면 정보 조회",
        description = "사용자의 홈 화면에 표시될 정보를 조회합니다. 월별/연간 일기 수, 감정 통계, 추천 복지 서비스, 척도 질문 필요 여부 등을 포함합니다."
    )
    @ApiResponses(
        value = [
            ApiResponse(responseCode = "200", description = "홈 화면 정보 조회 성공"),
            ApiResponse(responseCode = "401", description = "인증 실패")
        ]
    )
    fun getHome(
        @Parameter(hidden = true) @UserId userId: UUID
    ): CommonResponse<HomeResponse>
}