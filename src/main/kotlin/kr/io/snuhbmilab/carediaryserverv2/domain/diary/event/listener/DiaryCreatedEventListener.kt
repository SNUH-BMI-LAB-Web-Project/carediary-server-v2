package kr.io.snuhbmilab.carediaryserverv2.domain.diary.event.listener

import io.github.oshai.kotlinlogging.KotlinLogging
import kr.io.snuhbmilab.carediaryserverv2.domain.diary.event.DiaryCreatedEvent
import kr.io.snuhbmilab.carediaryserverv2.external.sqs.SqsMessageSender
import kr.io.snuhbmilab.carediaryserverv2.external.sqs.dto.DiaryAnalysisRequest
import org.springframework.stereotype.Component
import org.springframework.transaction.event.TransactionPhase
import org.springframework.transaction.event.TransactionalEventListener

private val logger = KotlinLogging.logger {}

@Component
class DiaryCreatedEventListener(private val sqsMessageSender: SqsMessageSender) {
    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    fun onDiaryCreated(event: DiaryCreatedEvent) {
        try {
            sqsMessageSender.sendAsync(
                DiaryAnalysisRequest.of(
                    diaryId = event.diaryId,
                    uploaderId = event.uploaderId,
                )
            )
        } catch (e: Exception) {
            logger.error(e) { "이벤트 수신 SQS 발송 실패: ${event.diaryId}" }
        }
    }
}