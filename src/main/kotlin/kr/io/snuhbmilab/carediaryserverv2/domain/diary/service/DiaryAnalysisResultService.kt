package kr.io.snuhbmilab.carediaryserverv2.domain.diary.service

import kr.io.snuhbmilab.carediaryserverv2.domain.diary.repository.DiaryAnalysisResultRepository
import kr.io.snuhbmilab.carediaryserverv2.domain.diary.repository.DiaryKeywordExtractionRepository
import kr.io.snuhbmilab.carediaryserverv2.domain.diary.repository.DiaryWelfareServiceRepository
import kr.io.snuhbmilab.carediaryserverv2.domain.diary.repository.PieRepository
import org.springframework.stereotype.Service
import java.time.LocalDateTime
import java.util.UUID

@Service
class DiaryAnalysisResultService(
    private val diaryAnalysisResultRepository: DiaryAnalysisResultRepository,
    private val pieRepository: PieRepository,
    private val diaryKeywordExtractionRepository: DiaryKeywordExtractionRepository,
    private val diaryWelfareServiceRepository: DiaryWelfareServiceRepository
) {
    fun findAllPie(diaryId: UUID) = pieRepository.findAllByDiaryId(diaryId)

    fun findAllKeywordExtractions(diaryId: UUID) = diaryKeywordExtractionRepository.findAllByDiaryId(diaryId)
    fun findAllWelfareServices(diaryId: UUID) = diaryWelfareServiceRepository.findAllByDiaryId(diaryId)

    fun countAll(): Long = diaryAnalysisResultRepository.count()

    fun countByCreatedAtAfter(startDateTime: LocalDateTime): Long =
        diaryAnalysisResultRepository.countByCreatedAtAfterOrEqual(startDateTime)

    fun countByUserId(userId: UUID): Long = diaryAnalysisResultRepository.countByUploaderId(userId)

    fun countByUserIds(userIds: List<UUID>): Map<UUID, Long> {
        if (userIds.isEmpty()) return emptyMap()
        return diaryAnalysisResultRepository.countByUploaderIdIn(userIds)
            .associate { it.uploaderId to it.count }
    }
}