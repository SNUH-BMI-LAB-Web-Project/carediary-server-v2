package kr.io.snuhbmilab.carediaryserverv2.admin.facade

import kr.io.snuhbmilab.carediaryserverv2.admin.dto.response.AdminDiaryFindAllResponse
import kr.io.snuhbmilab.carediaryserverv2.admin.dto.response.AdminDiarySdohResponse
import kr.io.snuhbmilab.carediaryserverv2.domain.diary.service.DiaryAnalysisResultService
import kr.io.snuhbmilab.carediaryserverv2.domain.diary.service.DiaryRecommendedQuestionService
import kr.io.snuhbmilab.carediaryserverv2.domain.diary.service.DiaryService
import kr.io.snuhbmilab.carediaryserverv2.domain.user.service.UserService
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.time.LocalDate
import java.util.UUID

@Service
@Transactional(readOnly = true)
class AdminDiaryFacade(
    private val diaryAnalysisResultService: DiaryAnalysisResultService,
    private val diaryService: DiaryService,
    private val diaryRecommendedQuestionService: DiaryRecommendedQuestionService,
    private val userService: UserService
) {
    fun findSdoh(diaryId: UUID): AdminDiarySdohResponse {
        diaryService.validateExists(diaryId)
        val pieList = diaryAnalysisResultService.findAllPie(diaryId)

        return AdminDiarySdohResponse.from(pieList)
    }

    fun findAllByUserIdAndDate(userId: UUID, date: LocalDate): AdminDiaryFindAllResponse {
        userService.validateExists(userId)
        val diaries = diaryService.findAllByUserIdAndDate(userId, date)
        val diaryIds = diaries.mapNotNull { it.id }
        val questionScoresMap = diaryRecommendedQuestionService.findAllMapByDiaryIds(diaryIds)
        val keywordExtractions = diaryAnalysisResultService.findKeywordExtractionsMapByDiaryIds(diaryIds)
        val welfareServices = diaryAnalysisResultService.findWelfareServicesMapByDiaryIds(diaryIds)

        return AdminDiaryFindAllResponse.of(diaries, questionScoresMap, keywordExtractions, welfareServices)
    }

    @Transactional
    fun updateWelfareServiceVisible(diaryId: UUID, welfareServiceId: Long) {
        diaryService.validateExists(diaryId)
        diaryAnalysisResultService.updateWelfareServiceVisible(diaryId, welfareServiceId)
    }

    @Transactional
    fun updateWelfareServiceInvisible(diaryId: UUID, welfareServiceId: Long) {
        diaryService.validateExists(diaryId)
        diaryAnalysisResultService.updateWelfareServiceInvisible(diaryId, welfareServiceId)
    }
}