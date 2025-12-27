package kr.io.snuhbmilab.carediaryserverv2.domain.scalequestion.controller

import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.Parameter
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.responses.ApiResponses
import io.swagger.v3.oas.annotations.security.SecurityRequirement
import io.swagger.v3.oas.annotations.tags.Tag
import jakarta.validation.Valid
import kr.io.snuhbmilab.carediaryserverv2.common.annotation.UserId
import kr.io.snuhbmilab.carediaryserverv2.common.dto.CommonResponse
import kr.io.snuhbmilab.carediaryserverv2.domain.scalequestion.dto.request.ScaleQuestionUserAnswerRegisterRequest
import kr.io.snuhbmilab.carediaryserverv2.domain.scalequestion.dto.response.ScaleQuestionFindAllResponse
import kr.io.snuhbmilab.carediaryserverv2.domain.scalequestion.dto.response.UserScaleFindAllResponse
import org.springframework.web.bind.annotation.RequestBody
import java.util.UUID

@Tag(name = "Scale Question", description = "척도 질문 관련 API")
@SecurityRequirement(name = "bearerAuth")
interface ScaleQuestionApi {

    @Operation(
        summary = "척도 질문 응답 등록",
        description = "사용자의 척도 질문에 대한 응답을 등록합니다. 불안, 우울, 분노 척도에 대한 응답을 포함합니다."
    )
    @ApiResponses(
        value = [
            ApiResponse(responseCode = "200", description = "응답 등록 성공"),
            ApiResponse(responseCode = "400", description = "잘못된 요청 (유효성 검사 실패)"),
            ApiResponse(responseCode = "401", description = "인증 실패")
        ]
    )
    fun registerUserScaleQuestionResult(
        @Parameter(hidden = true) @UserId userId: UUID,
        @Valid @RequestBody request: ScaleQuestionUserAnswerRegisterRequest
    ): CommonResponse<Unit>

    @Operation(
        summary = "전체 척도 질문 목록 조회",
        description = "시스템에 등록된 모든 척도 질문을 조회합니다. 불안, 우울, 분노 카테고리별 질문을 포함합니다."
    )
    @ApiResponses(
        value = [
            ApiResponse(responseCode = "200", description = "척도 질문 목록 조회 성공"),
            ApiResponse(responseCode = "401", description = "인증 실패")
        ]
    )
    fun findAllScaleQuestions(): CommonResponse<ScaleQuestionFindAllResponse>

    @Operation(
        summary = "사용자 척도 결과 조회",
        description = "현재 사용자의 모든 척도 측정 결과를 조회합니다. 회차별로 그룹화된 결과를 반환합니다."
    )
    @ApiResponses(
        value = [
            ApiResponse(responseCode = "200", description = "척도 결과 조회 성공"),
            ApiResponse(responseCode = "401", description = "인증 실패")
        ]
    )
    fun findAllUserScales(
        @Parameter(hidden = true) @UserId userId: UUID
    ): CommonResponse<UserScaleFindAllResponse>
}