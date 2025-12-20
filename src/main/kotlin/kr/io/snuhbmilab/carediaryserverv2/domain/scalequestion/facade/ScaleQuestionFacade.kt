package kr.io.snuhbmilab.carediaryserverv2.domain.scalequestion.facade

import kr.io.snuhbmilab.carediaryserverv2.domain.scalequestion.dto.request.ScaleQuestionUserAnswerRegisterRequest
import kr.io.snuhbmilab.carediaryserverv2.domain.scalequestion.dto.response.ScaleQuestionFindAllResponse
import kr.io.snuhbmilab.carediaryserverv2.domain.scalequestion.service.ScaleQuestionService
import kr.io.snuhbmilab.carediaryserverv2.domain.user.service.UserService
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.util.UUID

@Service
@Transactional(readOnly = true)
class ScaleQuestionFacade(
    private val scaleQuestionService: ScaleQuestionService,
    private val userService: UserService
) {
    /**
     * Registers scale-question answers for the specified user.
     *
     * Persists each answer contained in the request and associates it with the user identified by [userId].
     *
     * @param userId UUID of the user whose answers will be recorded.
     * @param request DTO containing the collection of scale-question answer items to register.
     */
    @Transactional
    fun registerUserScaleQuestionResult(userId: UUID, request: ScaleQuestionUserAnswerRegisterRequest) {
        val user = userService.findById(userId)

        request.items.forEach {
            scaleQuestionService.appendUserAnswer(user, it.scaleQuestionId, it.answer.toString())
        }
    }

    /**
     * Get all scale questions packaged into a response DTO.
     *
     * @return A [ScaleQuestionFindAllResponse] containing all scale questions.
     */
    fun findAllScaleQuestions(): ScaleQuestionFindAllResponse {
        return ScaleQuestionFindAllResponse.from(scaleQuestionService.findAll())
    }
}