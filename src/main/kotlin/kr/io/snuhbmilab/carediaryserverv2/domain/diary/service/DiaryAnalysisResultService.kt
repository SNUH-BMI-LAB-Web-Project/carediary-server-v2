package kr.io.snuhbmilab.carediaryserverv2.domain.diary.service

import kr.io.snuhbmilab.carediaryserverv2.domain.diary.repository.DiaryKeywordExtractionRepository
import kr.io.snuhbmilab.carediaryserverv2.domain.diary.repository.DiaryWelfareServiceRepository
import kr.io.snuhbmilab.carediaryserverv2.domain.diary.repository.PieRepository
import org.springframework.stereotype.Service
import java.util.UUID

@Service
class DiaryAnalysisResultService(
    private val pieRepository: PieRepository,
    private val diaryKeywordExtractionRepository: DiaryKeywordExtractionRepository,
    private val diaryWelfareServiceRepository: DiaryWelfareServiceRepository
) {
    fun findAllPie(diaryId: UUID) = pieRepository.findAllByDiaryId(diaryId)

    fun findAllKeywordExtractions(diaryId: UUID) = diaryKeywordExtractionRepository.findAllByDiaryId(diaryId)
    fun findAllWelfareServices(diaryId: UUID) = diaryWelfareServiceRepository.findAllByDiaryId(diaryId)
}