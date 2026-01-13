package kr.io.snuhbmilab.carediaryserverv2.domain.scalequestion.service

import kr.io.snuhbmilab.carediaryserverv2.domain.scalequestion.constants.ScaleCategory
import kr.io.snuhbmilab.carediaryserverv2.domain.scalequestion.entity.ScaleQuestion
import kr.io.snuhbmilab.carediaryserverv2.domain.user.entity.User
import org.springframework.stereotype.Service

@Service
class UserScaleCalculator(
    private val scaleQuestionService: ScaleQuestionService,
    private val userScaleService: UserScaleService
) {

    //사용자 척도 질문 답변을 기반으로 분노/우울/불안별 점수 계산 + 저장
    fun calculate(user: User, answers: List<Pair<Long, Int>>) {
        val scaleQuestions = scaleQuestionService.findAllByIds(answers.map { it.first })
        val questionMap = scaleQuestions.associateBy { it.id!! }
        val result: List<Pair<ScaleQuestion, Int>> =
            answers.map { (id, value) -> questionMap[id]!! to value }

        val scores = hashMapOf<ScaleCategory, Int>()

        result.forEach { (scaleQuestion, answer) ->
            val category = scaleQuestion.scaleCategory
            // 역채점 문항인 경우: (옵션 개수 - 답변 + 1)로 점수 뒤집어서 계산
            val score = if (scaleQuestion.isReverseScored) scaleQuestion.optionCount - answer + 1 else answer
            val currentScore = scores.getOrDefault(category, 0)

            scores[category] = currentScore + score
        }

        scores.forEach { (category, score) ->
            val angerAdjustedScore = if (category == ScaleCategory.ANGER) score - 72 else score
            userScaleService.append(user, category, angerAdjustedScore)
        }
    }
}