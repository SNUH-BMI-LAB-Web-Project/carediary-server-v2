package kr.io.snuhbmilab.carediaryserverv2.admin.controller

import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.Parameter
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.responses.ApiResponses
import io.swagger.v3.oas.annotations.security.SecurityRequirement
import io.swagger.v3.oas.annotations.tags.Tag
import kr.io.snuhbmilab.carediaryserverv2.admin.dto.response.AdminDiaryFindAllResponse
import kr.io.snuhbmilab.carediaryserverv2.admin.dto.response.AdminDiaryKeywordResponse
import kr.io.snuhbmilab.carediaryserverv2.admin.dto.response.AdminDiarySdohResponse
import kr.io.snuhbmilab.carediaryserverv2.admin.dto.response.AdminDiaryWelfareServiceResponse
import kr.io.snuhbmilab.carediaryserverv2.common.dto.CommonResponse
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestParam
import java.time.LocalDate
import java.util.UUID

@Tag(name = "(Admin) Diary", description = "관리자 일기 관련 API")
@SecurityRequirement(name = "JWT")
interface AdminDiaryApi {

    @Operation(
        summary = "일기 SDoH 조회",
        description = "특정 일기의 사회적 건강 결정요인(Social Determinants of Health) 정보를 조회합니다."
    )
    @ApiResponses(
        value = [
            ApiResponse(responseCode = "200", description = "SDoH 조회 성공"),
            ApiResponse(responseCode = "401", description = "인증 실패"),
            ApiResponse(responseCode = "403", description = "권한 없음"),
            ApiResponse(responseCode = "404", description = "일기를 찾을 수 없음")
        ]
    )
    fun findSdoh(
        @Parameter(description = "일기 ID", required = true)
        @PathVariable diaryId: UUID
    ): CommonResponse<AdminDiarySdohResponse>

    @Operation(
        summary = "일기 추출 키워드 조회",
        description = "특정 일기에서 추출된 키워드 정보를 조회합니다."
    )
    @ApiResponses(
        value = [
            ApiResponse(responseCode = "200", description = "키워드 조회 성공"),
            ApiResponse(responseCode = "401", description = "인증 실패"),
            ApiResponse(responseCode = "403", description = "권한 없음"),
            ApiResponse(responseCode = "404", description = "일기를 찾을 수 없음")
        ]
    )
    fun findExtractedKeywords(
        @Parameter(description = "일기 ID", required = true)
        @PathVariable diaryId: UUID
    ): CommonResponse<AdminDiaryKeywordResponse>

    @Operation(
        summary = "일기 복지로 서비스 조회",
        description = "특정 일기와 연관된 복지로 서비스 정보를 조회합니다."
    )
    @ApiResponses(
        value = [
            ApiResponse(responseCode = "200", description = "복지 서비스 조회 성공"),
            ApiResponse(responseCode = "401", description = "인증 실패"),
            ApiResponse(responseCode = "403", description = "권한 없음"),
            ApiResponse(responseCode = "404", description = "일기를 찾을 수 없음")
        ]
    )
    fun findWelfareServices(
        @Parameter(description = "일기 ID", required = true)
        @PathVariable diaryId: UUID
    ): CommonResponse<AdminDiaryWelfareServiceResponse>

    @Operation(
        summary = "사용자별 일기 목록 조회",
        description = "특정 사용자가 특정 날짜에 작성한 일기 목록을 조회합니다."
    )
    @ApiResponses(
        value = [
            ApiResponse(responseCode = "200", description = "일기 목록 조회 성공"),
            ApiResponse(responseCode = "401", description = "인증 실패"),
            ApiResponse(responseCode = "403", description = "권한 없음")
        ]
    )
    fun findAllByUserIdAndDate(
        @Parameter(description = "사용자 ID", required = true)
        @RequestParam userId: UUID,
        @Parameter(description = "작성일 (yyyy-MM-dd)", required = true)
        @RequestParam date: LocalDate
    ): CommonResponse<AdminDiaryFindAllResponse>
}