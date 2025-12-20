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
    @Transactional
    fun registerUserScaleQuestionResult(userId: UUID, request: ScaleQuestionUserAnswerRegisterRequest) {
        val user = userService.findById(userId)

        request.items.forEach {
            scaleQuestionService.appendUserAnswer(user, it.scaleQuestionId, it.answer.toString())
        }
    }

    fun findAllScaleQuestions(): ScaleQuestionFindAllResponse {
        return ScaleQuestionFindAllResponse.from(scaleQuestionService.findAll())
    }
}
