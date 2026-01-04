package kr.io.snuhbmilab.carediaryserverv2.domain.scalequestion.controller

import jakarta.validation.Valid
import kr.io.snuhbmilab.carediaryserverv2.common.SuccessMessage
import kr.io.snuhbmilab.carediaryserverv2.common.annotation.UserId
import kr.io.snuhbmilab.carediaryserverv2.common.dto.CommonResponse
import kr.io.snuhbmilab.carediaryserverv2.domain.scalequestion.dto.request.ScaleQuestionUserAnswerRegisterRequest
import kr.io.snuhbmilab.carediaryserverv2.domain.scalequestion.dto.response.ScaleQuestionFindAllResponse
import kr.io.snuhbmilab.carediaryserverv2.domain.scalequestion.dto.response.UserScaleFindAllResponse
import kr.io.snuhbmilab.carediaryserverv2.domain.scalequestion.facade.ScaleQuestionFacade
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController
import java.util.UUID

@RestController
class ScaleQuestionController(
    private val scaleQuestionFacade: ScaleQuestionFacade,
) : ScaleQuestionApi {

    @PostMapping("/v1/users/scale-questions")
    override fun registerUserScaleQuestionResult(
        @UserId userId: UUID,
        @Valid @RequestBody request: ScaleQuestionUserAnswerRegisterRequest
    ): CommonResponse<Unit> {
        scaleQuestionFacade.registerUserScaleQuestionResult(userId, request)
        return CommonResponse.ok(SuccessMessage.SCALE_QUESTION_ANSWER_REGISTERED)
    }

    @GetMapping("/v1/scale-questions")
    override fun findAllScaleQuestions(): CommonResponse<ScaleQuestionFindAllResponse> {
        return CommonResponse.ok(scaleQuestionFacade.findAllScaleQuestions())
    }

    @GetMapping("/v1/users/scales")
    override fun findAllUserScales(
        @UserId userId: UUID
    ) : CommonResponse<UserScaleFindAllResponse> {
        return CommonResponse.ok(scaleQuestionFacade.findAllUserScales(userId))
    }
}