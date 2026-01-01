package kr.io.snuhbmilab.carediaryserverv2.external.model

import kr.io.snuhbmilab.carediaryserverv2.external.model.dto.GenerateSummaryRequest
import kr.io.snuhbmilab.carediaryserverv2.external.model.dto.GenerateSummaryResponse
import org.springframework.cloud.openfeign.FeignClient
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody

@FeignClient(name = "model-client", url = $$"${external-api.model-uri}")
interface ModelClient {
    @PostMapping("/summary")
    fun generateSummary(@RequestBody request: GenerateSummaryRequest): ResponseEntity<GenerateSummaryResponse>
}