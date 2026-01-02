package kr.io.snuhbmilab.carediaryserverv2.admin.facade

import kr.io.snuhbmilab.carediaryserverv2.admin.dto.response.AdminDiaryKeywordResponse
import kr.io.snuhbmilab.carediaryserverv2.admin.dto.response.AdminDiarySdohResponse
import kr.io.snuhbmilab.carediaryserverv2.admin.dto.response.AdminDiaryWelfareServiceResponse
import kr.io.snuhbmilab.carediaryserverv2.domain.diary.service.DiaryAnalysisResultService
import kr.io.snuhbmilab.carediaryserverv2.domain.diary.service.DiaryService
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.util.UUID

@Service
@Transactional(readOnly = true)
class AdminDiaryFacade(
    private val diaryAnalysisResultService: DiaryAnalysisResultService,
    private val diaryService: DiaryService
) {
    fun findSdoh(diaryId: UUID): AdminDiarySdohResponse {
        diaryService.validateExists(diaryId)
        val pieList = diaryAnalysisResultService.findAllPie(diaryId)

        return AdminDiarySdohResponse.from(pieList)
    }

    fun findExtractedKeywords(diaryId: UUID): AdminDiaryKeywordResponse {
        diaryService.validateExists(diaryId)
        val keywords = diaryAnalysisResultService.findAllKeywordExtractions(diaryId)

        return AdminDiaryKeywordResponse.of(diaryId, keywords)
    }

    fun findWelfareServices(diaryId: UUID): AdminDiaryWelfareServiceResponse {
        diaryService.validateExists(diaryId)
        val welfareServices = diaryAnalysisResultService.findAllWelfareServices(diaryId)

        return AdminDiaryWelfareServiceResponse.of(diaryId, welfareServices)
    }
}