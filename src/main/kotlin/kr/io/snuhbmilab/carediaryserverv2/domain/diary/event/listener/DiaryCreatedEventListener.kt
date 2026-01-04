package kr.io.snuhbmilab.carediaryserverv2.domain.diary.event.listener

import kr.io.snuhbmilab.carediaryserverv2.domain.diary.event.DiaryCreatedEvent
import kr.io.snuhbmilab.carediaryserverv2.external.sqs.SqsMessageSender
import kr.io.snuhbmilab.carediaryserverv2.external.sqs.dto.DiaryAnalysisRequest
import org.springframework.context.event.EventListener
import org.springframework.stereotype.Component

@Component
class DiaryCreatedEventListener(private val sqsMessageSender: SqsMessageSender) {
    @EventListener
    fun onDiaryCreated(event: DiaryCreatedEvent) {
        sqsMessageSender.sendAsync(
            DiaryAnalysisRequest.of(
                diaryId = event.diaryId,
                uploaderId = event.uploaderId,
            )
        )
    }
}