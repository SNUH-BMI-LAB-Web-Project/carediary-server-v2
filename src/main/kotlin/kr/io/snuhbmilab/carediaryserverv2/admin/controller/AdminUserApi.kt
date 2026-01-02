package kr.io.snuhbmilab.carediaryserverv2.admin.controller

import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.Parameter
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.responses.ApiResponses
import io.swagger.v3.oas.annotations.security.SecurityRequirement
import io.swagger.v3.oas.annotations.tags.Tag
import kr.io.snuhbmilab.carediaryserverv2.common.dto.CommonResponse
import kr.io.snuhbmilab.carediaryserverv2.admin.dto.response.AdminUserDetailResponse
import kr.io.snuhbmilab.carediaryserverv2.admin.dto.response.AdminUserFindAllResponse
import kr.io.snuhbmilab.carediaryserverv2.admin.dto.response.AdminUserScaleFindAllResponse
import kr.io.snuhbmilab.carediaryserverv2.admin.dto.response.AdminUserScaleQuestionResultResponse
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestParam
import java.util.UUID

@Tag(name = "(Admin) User", description = "관리자 사용자 관련 API")
@SecurityRequirement(name = "JWT")
interface AdminUserApi {

    @Operation(
        summary = "전체 사용자 목록 조회",
        description = "시스템에 등록된 모든 사용자의 목록을 조회합니다."
    )
    @ApiResponses(
        value = [
            ApiResponse(responseCode = "200", description = "사용자 목록 조회 성공"),
            ApiResponse(responseCode = "401", description = "인증 실패"),
            ApiResponse(responseCode = "403", description = "권한 없음")
        ]
    )
    fun findAllUsers(): CommonResponse<AdminUserFindAllResponse>

    @Operation(
        summary = "사용자 상세 조회",
        description = "특정 사용자의 상세 정보를 조회합니다."
    )
    @ApiResponses(
        value = [
            ApiResponse(responseCode = "200", description = "사용자 상세 조회 성공"),
            ApiResponse(responseCode = "401", description = "인증 실패"),
            ApiResponse(responseCode = "403", description = "권한 없음"),
            ApiResponse(responseCode = "404", description = "사용자를 찾을 수 없음")
        ]
    )
    fun findUserById(
        @Parameter(description = "사용자 ID", required = true)
        @PathVariable userId: UUID
    ): CommonResponse<AdminUserDetailResponse>

    @Operation(
        summary = "사용자 척도 점수 조회",
        description = "특정 사용자의 모든 척도 측정 결과를 조회합니다."
    )
    @ApiResponses(
        value = [
            ApiResponse(responseCode = "200", description = "척도 결과 조회 성공"),
            ApiResponse(responseCode = "401", description = "인증 실패"),
            ApiResponse(responseCode = "403", description = "권한 없음"),
            ApiResponse(responseCode = "404", description = "사용자를 찾을 수 없음")
        ]
    )
    fun findUserScales(
        @Parameter(description = "사용자 ID", required = true)
        @PathVariable userId: UUID
    ): CommonResponse<AdminUserScaleFindAllResponse>

    @Operation(
        summary = "사용자 척도 설문 답변 조회",
        description = "특정 사용자의 특정 회차 척도 질문에 대한 응답 결과를 조회합니다."
    )
    @ApiResponses(
        value = [
            ApiResponse(responseCode = "200", description = "척도 질문 응답 조회 성공"),
            ApiResponse(responseCode = "400", description = "잘못된 요청 (유효하지 않은 회차)"),
            ApiResponse(responseCode = "401", description = "인증 실패"),
            ApiResponse(responseCode = "403", description = "권한 없음"),
            ApiResponse(responseCode = "404", description = "사용자 또는 응답을 찾을 수 없음")
        ]
    )
    fun findScaleQuestionResult(
        @Parameter(description = "사용자 ID", required = true)
        @PathVariable userId: UUID,
        @Parameter(description = "조회할 회차", required = true, example = "1")
        @RequestParam count: Int
    ): CommonResponse<AdminUserScaleQuestionResultResponse>
}