package kr.io.snuhbmilab.carediaryserverv2.external.sqs.listener

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import io.awspring.cloud.sqs.annotation.SqsListener
import io.github.oshai.kotlinlogging.KotlinLogging
import kr.io.snuhbmilab.carediaryserverv2.domain.diary.service.DiaryAnalysisResultService
import kr.io.snuhbmilab.carediaryserverv2.external.sqs.dto.DiaryAnalysisResponse
import org.springframework.stereotype.Component

private val logger = KotlinLogging.logger {}

@Component
class DiaryAnalysisResponseListener(
    private val diaryAnalysisResultService: DiaryAnalysisResultService
) {

    @SqsListener(
        value = [$$"${aws.sqs.model-output-queue-url}"],
        maxConcurrentMessages = "10",
        maxMessagesPerPoll = "10"
    )
    fun onMessage(payload: String) {
        try {
            val response = objectMapper.readValue<DiaryAnalysisResponse>(payload)

            logger.info { "SQS 분석 응답 수신 - diaryId: ${response.diaryId}, PIE-items: ${response.items.size}개" }

            if (response.items.isEmpty()) {
                throw IllegalArgumentException("분석 내용 부족으로 PIE 툴 분석 결과가 없습니다.")
            }

            diaryAnalysisResultService.saveAnalysisResult(response)
            logger.info { "분석 결과 저장 완료 - diaryId: ${response.diaryId}" }
        } catch (e: Exception) {
            logger.error(e) { "SQS 분석 결과 저장 실패" }
        }
    }

    companion object {
        private val objectMapper = jacksonObjectMapper()
    }
}