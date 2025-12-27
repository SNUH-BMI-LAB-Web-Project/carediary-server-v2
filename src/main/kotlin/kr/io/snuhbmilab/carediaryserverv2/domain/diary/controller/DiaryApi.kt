package kr.io.snuhbmilab.carediaryserverv2.domain.diary.controller

import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.Parameter
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.responses.ApiResponses
import io.swagger.v3.oas.annotations.security.SecurityRequirement
import io.swagger.v3.oas.annotations.tags.Tag
import jakarta.validation.Valid
import kr.io.snuhbmilab.carediaryserverv2.common.annotation.UserId
import kr.io.snuhbmilab.carediaryserverv2.common.dto.CommonResponse
import kr.io.snuhbmilab.carediaryserverv2.domain.diary.dto.request.DiaryCreateRequest
import kr.io.snuhbmilab.carediaryserverv2.domain.diary.dto.response.DiaryCreateResponse
import kr.io.snuhbmilab.carediaryserverv2.domain.diary.dto.response.DiaryDatesResponse
import kr.io.snuhbmilab.carediaryserverv2.domain.diary.dto.response.DiaryDetailResponse
import kr.io.snuhbmilab.carediaryserverv2.domain.diary.dto.response.DiaryFindAllResponse
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestParam
import java.time.LocalDate
import java.time.YearMonth
import java.util.UUID

@Tag(name = "Diary", description = "일기 관련 API")
@SecurityRequirement(name = "JWT")
interface DiaryApi {

    @Operation(
        summary = "일기 생성",
        description = "새로운 일기를 작성합니다. 날짜, 내용, 감정, 질문 점수를 포함해야 합니다."
    )
    @ApiResponses(
        value = [
            ApiResponse(responseCode = "200", description = "일기 생성 성공"),
            ApiResponse(responseCode = "400", description = "잘못된 요청 (유효성 검사 실패)"),
            ApiResponse(responseCode = "401", description = "인증 실패")
        ]
    )
    fun createDiary(
        @Parameter(hidden = true) @UserId userId: UUID,
        @Valid @RequestBody request: DiaryCreateRequest
    ): CommonResponse<DiaryCreateResponse>

    @Operation(
        summary = "내 일기 목록 조회",
        description = "현재 사용자의 일기 목록을 조회합니다. 선택적으로 시작/종료 날짜로 필터링할 수 있습니다."
    )
    @ApiResponses(
        value = [
            ApiResponse(responseCode = "200", description = "일기 목록 조회 성공"),
            ApiResponse(responseCode = "401", description = "인증 실패")
        ]
    )
    fun findAllDiariesByMe(
        @Parameter(hidden = true) @UserId userId: UUID,
        @Parameter(description = "조회 시작 날짜 (선택)", example = "2024-01-01")
        @RequestParam(required = false) startDate: LocalDate? = null,
        @Parameter(description = "조회 종료 날짜 (선택)", example = "2024-12-31")
        @RequestParam(required = false) endDate: LocalDate? = null
    ): CommonResponse<DiaryFindAllResponse>

    @Operation(
        summary = "일기 상세 조회",
        description = "특정 일기의 상세 정보를 조회합니다."
    )
    @ApiResponses(
        value = [
            ApiResponse(responseCode = "200", description = "일기 상세 조회 성공"),
            ApiResponse(responseCode = "401", description = "인증 실패"),
            ApiResponse(responseCode = "404", description = "일기를 찾을 수 없음")
        ]
    )
    fun findDiaryById(
        @Parameter(hidden = true) @UserId userId: UUID,
        @Parameter(description = "일기 ID", required = true)
        @PathVariable diaryId: UUID
    ): CommonResponse<DiaryDetailResponse>

    @Operation(
        summary = "월별 일기 작성 날짜 조회",
        description = "특정 월에 일기가 작성된 날짜 목록을 조회합니다."
    )
    @ApiResponses(
        value = [
            ApiResponse(responseCode = "200", description = "날짜 목록 조회 성공"),
            ApiResponse(responseCode = "401", description = "인증 실패")
        ]
    )
    fun findDates(
        @Parameter(hidden = true) @UserId userId: UUID,
        @Parameter(description = "조회할 월 (yyyy-MM 형식)", required = true, example = "2024-01")
        @RequestParam month: YearMonth
    ): CommonResponse<DiaryDatesResponse>
}