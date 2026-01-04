package kr.io.snuhbmilab.carediaryserverv2.external.sqs

import io.awspring.cloud.sqs.operations.SqsTemplate
import io.github.oshai.kotlinlogging.KotlinLogging
import org.springframework.stereotype.Component

private val logger = KotlinLogging.logger {}

@Component
class SqsMessageSender(
    private val sqsTemplate: SqsTemplate,
    private val sqsProperties: SqsProperties
) {

    fun <T : Any> send(message: T) {
        logger.info { "SQS 메시지 전송: $message" }
        sqsTemplate.send { sendOptions ->
            sendOptions
                .queue(sqsProperties.modelInputQueueUrl)
                .payload(message)
        }
        logger.info { "SQS 메시지 전송 성공" }
    }

    fun <T : Any> sendAsync(message: T) {
        logger.info { "SQS 메시지 비동기 전송: $message" }
        sqsTemplate.sendAsync { sendOptions ->
            sendOptions
                .queue(sqsProperties.modelInputQueueUrl)
                .payload(message)
        }.thenAccept { result ->
            logger.info { "SQS 메시지 비동기 전송 성공 - messageId: ${result.messageId()}" }
        }.exceptionally { throwable ->
            logger.error(throwable) { "SQS 메시지 비동기 전송 실패" }
            null
        }
    }
}
