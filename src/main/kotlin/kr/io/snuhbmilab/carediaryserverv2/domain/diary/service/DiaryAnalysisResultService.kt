package kr.io.snuhbmilab.carediaryserverv2.domain.diary.service

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import kr.io.snuhbmilab.carediaryserverv2.common.exception.BusinessException
import kr.io.snuhbmilab.carediaryserverv2.common.utils.joinToStringDBText
import kr.io.snuhbmilab.carediaryserverv2.domain.diary.entity.DiaryAnalysisResult
import kr.io.snuhbmilab.carediaryserverv2.domain.diary.entity.DiaryWelfareServiceEntity
import kr.io.snuhbmilab.carediaryserverv2.domain.diary.entity.Pie
import kr.io.snuhbmilab.carediaryserverv2.domain.diary.event.DiaryAnalysisResultSavedEvent
import kr.io.snuhbmilab.carediaryserverv2.domain.diary.exception.DiaryErrorCode
import kr.io.snuhbmilab.carediaryserverv2.domain.diary.repository.DiaryAnalysisResultRepository
import kr.io.snuhbmilab.carediaryserverv2.domain.diary.repository.DiaryKeywordExtractionRepository
import kr.io.snuhbmilab.carediaryserverv2.domain.diary.repository.DiaryWelfareServiceRepository
import kr.io.snuhbmilab.carediaryserverv2.domain.diary.repository.PieRepository
import kr.io.snuhbmilab.carediaryserverv2.external.sqs.dto.DiaryAnalysisResponse
import org.springframework.context.ApplicationEventPublisher
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.time.LocalDateTime
import java.util.UUID

@Service
class DiaryAnalysisResultService(
    private val diaryAnalysisResultRepository: DiaryAnalysisResultRepository,
    private val pieRepository: PieRepository,
    private val diaryKeywordExtractionRepository: DiaryKeywordExtractionRepository,
    private val diaryWelfareServiceRepository: DiaryWelfareServiceRepository,
    private val diaryService: DiaryService,
    private val applicationEventPublisher: ApplicationEventPublisher,
) {
    fun findAllPie(diaryId: UUID) = pieRepository.findAllByDiaryId(diaryId)

    fun findAllKeywordExtractions(diaryId: UUID) = diaryKeywordExtractionRepository.findAllByDiaryId(diaryId)
    fun findAllWelfareServices(diaryId: UUID) = diaryWelfareServiceRepository.findAllByDiaryId(diaryId)

    fun findWelfareService(diaryId: UUID, welfareServiceId: Long): DiaryWelfareServiceEntity {
        return diaryWelfareServiceRepository.findByDiaryIdAndId(diaryId, welfareServiceId)
            ?: throw BusinessException(DiaryErrorCode.WELFARE_SERVICE_NOT_FOUND)
    }

    @Transactional
    fun updateWelfareServiceVisible(diaryId: UUID, welfareServiceId: Long) {
        val welfareService = findWelfareService(diaryId, welfareServiceId)
        welfareService.updateVisible()
    }

    @Transactional
    fun updateWelfareServiceInvisible(diaryId: UUID, welfareServiceId: Long) {
        val welfareService = findWelfareService(diaryId, welfareServiceId)
        welfareService.updateInvisible()
    }

    fun countAll(): Long = diaryAnalysisResultRepository.count()

    fun countByCreatedAtAfter(startDateTime: LocalDateTime): Long =
        diaryAnalysisResultRepository.countByCreatedAtAfterOrEqual(startDateTime)

    fun countByUserId(userId: UUID): Long = diaryAnalysisResultRepository.countByUploaderId(userId)

    fun countByUserIds(userIds: List<UUID>): Map<UUID, Long> {
        if (userIds.isEmpty()) return emptyMap()
        return diaryAnalysisResultRepository.countByUploaderIdIn(userIds)
            .associate { it.uploaderId to it.count }
    }

    @Transactional
    fun saveAnalysisResult(response: DiaryAnalysisResponse) {
        val diaryId = response.diaryId
        val diary = diaryService.findById(response.diaryId)

        val fullOutputText = objectMapper.writeValueAsString(response)

        val analysisResult = DiaryAnalysisResult(
            diary = diary,
            fullOutputText = fullOutputText
        )
        val savedAnalysisResult = diaryAnalysisResultRepository.save(analysisResult)

        val pies = response.items.map { item ->
            Pie(
                diaryAnalysisResult = savedAnalysisResult,
                diary = diary,
                elementNo = item.elementNo.toShort(),
                majorCat = item.majorCat,
                middleCat = item.middleCat,
                subCat = item.subCat,
                signCode = item.signCode,
                typeLabel = item.typeLabel,
                severity = item.severity.toShort(),
                duration = item.duration?.toShort(),
                coping = item.coping?.toShort(),
                recommendation = item.recommendation,
                evidences = item.evidences.joinToStringDBText(),
            )
        }
        pieRepository.saveAll(pies)

        applicationEventPublisher.publishEvent(DiaryAnalysisResultSavedEvent(diaryId, diary.uploaderId))
    }

    companion object {
        private val objectMapper = jacksonObjectMapper()
    }
}