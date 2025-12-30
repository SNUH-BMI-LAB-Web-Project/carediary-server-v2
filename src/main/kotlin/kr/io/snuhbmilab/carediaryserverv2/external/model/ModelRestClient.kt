package kr.io.snuhbmilab.carediaryserverv2.external.model

import kr.io.snuhbmilab.carediaryserverv2.external.ExternalApiProperties
import kr.io.snuhbmilab.carediaryserverv2.external.exception.ExternalException
import kr.io.snuhbmilab.carediaryserverv2.external.model.dto.GenerateSummaryRequest
import kr.io.snuhbmilab.carediaryserverv2.external.model.dto.GenerateSummaryResponse
import org.springframework.http.HttpHeaders
import org.springframework.http.MediaType
import org.springframework.stereotype.Component
import org.springframework.web.client.RestClient
import org.springframework.web.client.body

@Component
class ModelRestClient(
    externalApiProperties: ExternalApiProperties
) {
    private val restClient = RestClient.builder()
        .baseUrl(externalApiProperties.modelUri)
        .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
        .build()

    fun generateSummary(request: GenerateSummaryRequest) : GenerateSummaryResponse {
        return try {
            restClient.post()
                .uri("/summary")
                .body(request)
                .retrieve()
                .body<GenerateSummaryResponse>()!!
        } catch(exception: Exception) {
            throw ExternalException("LLM 모델 호출 오류입니다.", exception)
        }
    }
}