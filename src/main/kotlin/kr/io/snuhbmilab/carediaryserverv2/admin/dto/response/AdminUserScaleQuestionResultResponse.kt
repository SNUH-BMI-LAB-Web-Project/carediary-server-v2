package kr.io.snuhbmilab.carediaryserverv2.admin.dto.response

import kr.io.snuhbmilab.carediaryserverv2.common.utils.parseListFromDBText
import kr.io.snuhbmilab.carediaryserverv2.domain.scalequestion.constants.ScaleCategory
import kr.io.snuhbmilab.carediaryserverv2.domain.scalequestion.entity.ScaleQuestionUserAnswer
import kr.io.snuhbmilab.carediaryserverv2.domain.scalequestion.entity.UserScale
import kr.io.snuhbmilab.carediaryserverv2.domain.user.entity.User
import java.time.LocalDate
import java.util.UUID
import kotlin.math.max

data class AdminUserScaleQuestionResultResponse(
    val userId: UUID,
    val name: String,
    val birthDate: LocalDate,
    val primaryDiagnosis: String?,
    val scaleQuestionTermCount: Int,
    val items: Map<ScaleCategory, ScaleQuestionResultDto>
) {

    companion object {
        @JvmStatic
        fun of(
            user: User,
            userScales: List<UserScale>,
            userAnswerMap: Map<ScaleCategory, List<ScaleQuestionUserAnswer>>
        ): AdminUserScaleQuestionResultResponse {


            return AdminUserScaleQuestionResultResponse(
                userId = user.id!!,
                name = user.name!!,
                birthDate = user.birthDate!!,
                primaryDiagnosis = user.primaryDiagnosis,
                scaleQuestionTermCount = user.scaleQuestionTermCount,
                items = userAnswerMap.map { (scaleCategory, userAnswers) ->
                    scaleCategory to ScaleQuestionResultDto.from(
                        scaleCategory,
                        userScales.first { it.scaleCategory == scaleCategory }.score,
                        userAnswers
                    )
                }.toMap()
            )
        }
    }

    data class ScaleQuestionResultDto(
        val scaleCategory: ScaleCategory,
        val scaleScore: Int,
        val questions: List<ScaleQuestionItemDto>
    ) {
        companion object {
            @JvmStatic
            fun from(scaleCategory: ScaleCategory, scaleScore: Int, userAnswers: List<ScaleQuestionUserAnswer>) =
                ScaleQuestionResultDto(scaleCategory, scaleScore, userAnswers.map { ScaleQuestionItemDto.from(it) })
        }
    }

    data class ScaleQuestionItemDto(
        val questionNumber: Int,
        val questionText: String,
        val selectedOption: String
    ) {
        companion object {
            @JvmStatic
            fun from(scaleQuestionUserAnswer: ScaleQuestionUserAnswer): ScaleQuestionItemDto {
                val options = scaleQuestionUserAnswer.question!!.options.parseListFromDBText()

                return ScaleQuestionItemDto(
                    questionNumber = scaleQuestionUserAnswer.question!!.questionNumber,
                    questionText = scaleQuestionUserAnswer.question!!.content,
                    selectedOption = options[max(scaleQuestionUserAnswer.userAnswer - 1, 0)]
                )
            }
        }
    }
}
