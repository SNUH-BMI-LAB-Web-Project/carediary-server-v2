package kr.io.snuhbmilab.carediaryserverv2.domain.scalequestion.controller

import kr.io.snuhbmilab.carediaryserverv2.common.annotation.AuthenticationRequired
import kr.io.snuhbmilab.carediaryserverv2.common.annotation.UserId
import kr.io.snuhbmilab.carediaryserverv2.common.dto.CommonResponse
import kr.io.snuhbmilab.carediaryserverv2.domain.scalequestion.dto.request.ScaleQuestionUserAnswerRegisterRequest
import kr.io.snuhbmilab.carediaryserverv2.domain.scalequestion.dto.response.ScaleQuestionFindAllResponse
import kr.io.snuhbmilab.carediaryserverv2.domain.scalequestion.facade.ScaleQuestionFacade
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController
import java.util.UUID

@RestController
class ScaleQuestionController(
    private val scaleQuestionFacade: ScaleQuestionFacade
) {

    @PostMapping("/v1/users/scale-questions")
    fun registerUserScaleQuestionResult(
        @UserId userId: UUID,
        @RequestBody request: ScaleQuestionUserAnswerRegisterRequest
    ): CommonResponse<Unit> {
        scaleQuestionFacade.registerUserScaleQuestionResult(userId, request)
        return CommonResponse.ok()
    }

    @AuthenticationRequired
    @GetMapping("/v1/scale-questions")
    fun findAllScaleQuestions(): CommonResponse<ScaleQuestionFindAllResponse> {
        return CommonResponse.ok(scaleQuestionFacade.findAllScaleQuestions())
    }
}