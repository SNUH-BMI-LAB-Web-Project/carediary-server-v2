package kr.io.snuhbmilab.carediaryserverv2.external.sqs

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.stereotype.Component

@Component
@ConfigurationProperties(prefix = "aws.sqs")
data class SqsProperties(
    var modelInputQueueUrl: String = "",
    var modelOutputQueueUrl: String = "",
    var region: String = "ap-northeast-2"
)