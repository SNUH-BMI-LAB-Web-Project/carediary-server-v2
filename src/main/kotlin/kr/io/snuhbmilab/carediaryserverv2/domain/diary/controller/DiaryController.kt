package kr.io.snuhbmilab.carediaryserverv2.domain.diary.controller

import kr.io.snuhbmilab.carediaryserverv2.common.annotation.UserId
import kr.io.snuhbmilab.carediaryserverv2.common.dto.CommonResponse
import kr.io.snuhbmilab.carediaryserverv2.domain.diary.dto.request.DiaryCreateRequest
import kr.io.snuhbmilab.carediaryserverv2.domain.diary.facade.DiaryFacade
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.util.UUID

@RestController
@RequestMapping("/v1/diaries")
class DiaryController(
    private val diaryFacade: DiaryFacade
) {

    @PostMapping
    fun createDiary(@UserId userId: UUID, @RequestBody request: DiaryCreateRequest): CommonResponse<Unit> {
        diaryFacade.createDiary(userId, request)
        return CommonResponse.ok()
    }
}