package kr.io.snuhbmilab.carediaryserverv2.domain.diary.controller

import jakarta.validation.Valid
import kr.io.snuhbmilab.carediaryserverv2.common.SuccessMessage
import kr.io.snuhbmilab.carediaryserverv2.common.annotation.UserId
import kr.io.snuhbmilab.carediaryserverv2.common.dto.CommonResponse
import kr.io.snuhbmilab.carediaryserverv2.domain.diary.dto.request.DiaryCreateRequest
import kr.io.snuhbmilab.carediaryserverv2.domain.diary.dto.response.DiaryCreateResponse
import kr.io.snuhbmilab.carediaryserverv2.domain.diary.dto.response.DiaryDatesResponse
import kr.io.snuhbmilab.carediaryserverv2.domain.diary.dto.response.DiaryDetailResponse
import kr.io.snuhbmilab.carediaryserverv2.domain.diary.dto.response.DiaryFindAllResponse
import kr.io.snuhbmilab.carediaryserverv2.domain.diary.facade.DiaryFacade
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import java.time.LocalDate
import java.time.YearMonth
import java.util.UUID

@RestController
@RequestMapping("/v1/diaries")
class DiaryController(
    private val diaryFacade: DiaryFacade
) : DiaryApi {

    @PostMapping
    override fun createDiary(
        @UserId userId: UUID,
        @Valid @RequestBody request: DiaryCreateRequest
    ): CommonResponse<DiaryCreateResponse> {
        return CommonResponse.ok(SuccessMessage.DIARY_CREATED, diaryFacade.createDiary(userId, request))
    }

    @GetMapping("/me")
    override fun findAllDiariesByMe(
        @UserId userId: UUID,
        @RequestParam(required = false) startDate: LocalDate?,
        @RequestParam(required = false) endDate: LocalDate?
    ): CommonResponse<DiaryFindAllResponse> {
        return CommonResponse.ok(diaryFacade.findAllDiariesByMe(userId, startDate, endDate))
    }

    @GetMapping("/{diaryId}")
    override fun findDiaryById(
        @UserId userId: UUID,
        @PathVariable diaryId: UUID
    ): CommonResponse<DiaryDetailResponse> {
        return CommonResponse.ok(diaryFacade.findDiaryById(userId, diaryId))
    }

    @GetMapping("/dates")
    override fun findDates(
        @UserId userId: UUID,
        @RequestParam month: YearMonth
    ): CommonResponse<DiaryDatesResponse> {
        return CommonResponse.ok(diaryFacade.findDates(userId, month))
    }
}