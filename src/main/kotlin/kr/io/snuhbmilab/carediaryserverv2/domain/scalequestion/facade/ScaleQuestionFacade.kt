package kr.io.snuhbmilab.carediaryserverv2.domain.scalequestion.facade

import kr.io.snuhbmilab.carediaryserverv2.domain.scalequestion.dto.request.ScaleQuestionUserAnswerRegisterRequest
import kr.io.snuhbmilab.carediaryserverv2.domain.scalequestion.dto.response.ScaleQuestionFindAllResponse
import kr.io.snuhbmilab.carediaryserverv2.domain.scalequestion.dto.response.UserScaleFindAllResponse
import kr.io.snuhbmilab.carediaryserverv2.domain.scalequestion.event.UserScaleQuestionResultRegisterEvent
import kr.io.snuhbmilab.carediaryserverv2.domain.scalequestion.service.ScaleQuestionService
import kr.io.snuhbmilab.carediaryserverv2.domain.scalequestion.service.UserScaleCalculator
import kr.io.snuhbmilab.carediaryserverv2.domain.scalequestion.service.UserScaleService
import kr.io.snuhbmilab.carediaryserverv2.domain.user.service.UserService
import org.springframework.context.ApplicationEventPublisher
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.util.UUID

@Service
@Transactional(readOnly = true)
class ScaleQuestionFacade(
    private val scaleQuestionService: ScaleQuestionService,
    private val userService: UserService,
    private val userScaleCalculator: UserScaleCalculator,
    private val userScaleService: UserScaleService,
    private val applicationEventPublisher: ApplicationEventPublisher
) {
    @Transactional
    fun registerUserScaleQuestionResult(userId: UUID, request: ScaleQuestionUserAnswerRegisterRequest) {
        val user = userService.findById(userId)

        request.items.forEach {
            scaleQuestionService.appendUserAnswer(user, it.scaleQuestionId, it.answer)
        }

        //사용자 분노/우울/불안 점수 계산
        userScaleCalculator.calculate(user, request.items.map { it.scaleQuestionId to it.answer })

        user.addScaleQuestionTermCount()

        applicationEventPublisher.publishEvent(UserScaleQuestionResultRegisterEvent(userId))
    }

    fun findAllScaleQuestions(): ScaleQuestionFindAllResponse {
        return ScaleQuestionFindAllResponse.from(scaleQuestionService.findAll())
    }

    fun findAllUserScales(userId: UUID): UserScaleFindAllResponse {
        val user = userService.findById(userId)
        val userScales = userScaleService.findAll(user)

        return UserScaleFindAllResponse.from(userScales)
    }
}
