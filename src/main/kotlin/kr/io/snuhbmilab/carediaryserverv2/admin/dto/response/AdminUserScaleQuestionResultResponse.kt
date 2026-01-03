package kr.io.snuhbmilab.carediaryserverv2.admin.dto.response

import io.swagger.v3.oas.annotations.media.Schema
import kr.io.snuhbmilab.carediaryserverv2.common.utils.parseListFromDBText
import kr.io.snuhbmilab.carediaryserverv2.domain.scalequestion.constants.ScaleCategory
import kr.io.snuhbmilab.carediaryserverv2.domain.scalequestion.entity.ScaleQuestionUserAnswer
import kr.io.snuhbmilab.carediaryserverv2.domain.scalequestion.entity.UserScale
import kr.io.snuhbmilab.carediaryserverv2.domain.user.entity.User
import java.time.LocalDate
import java.util.UUID
import kotlin.math.max

@Schema(description = "관리자 사용자 척도 질문 응답 조회 결과")
data class AdminUserScaleQuestionResultResponse(
    @Schema(description = "사용자 ID", example = "550e8400-e29b-41d4-a716-446655440000")
    val userId: UUID,

    @Schema(description = "이름", example = "홍길동")
    val name: String,

    @Schema(description = "생년월일", example = "1990-01-15")
    val birthDate: LocalDate,

    @Schema(description = "주 진단명", example = "조현병")
    val primaryDiagnosis: String?,

    @Schema(description = "척도 질문 응답 회차", example = "3")
    val scaleQuestionTermCount: Int,

    @Schema(description = "척도 카테고리별 질문 응답 결과 (키: ANXIETY_DEPRESSION/ANGER)")
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
                        userScales.firstOrNull { it.scaleCategory == scaleCategory }?.score ?: 0,
                        userAnswers
                    )
                }.toMap()
            )
        }
    }

    @Schema(description = "척도 질문 결과 정보")
    data class ScaleQuestionResultDto(
        @Schema(description = "척도 카테고리 (ANXIETY_DEPRESSION: 불안/우울, ANGER: 분노)", example = "ANXIETY_DEPRESSION")
        val scaleCategory: ScaleCategory,

        @Schema(description = "해당 척도의 총점", example = "15")
        val scaleScore: Int,

        @Schema(description = "질문별 응답 목록")
        val questions: List<ScaleQuestionItemDto>
    ) {
        companion object {
            @JvmStatic
            fun from(scaleCategory: ScaleCategory, scaleScore: Int, userAnswers: List<ScaleQuestionUserAnswer>) =
                ScaleQuestionResultDto(scaleCategory, scaleScore, userAnswers.map { ScaleQuestionItemDto.from(it) })
        }
    }

    @Schema(description = "척도 질문 항목 정보")
    data class ScaleQuestionItemDto(
        @Schema(description = "질문 번호", example = "1")
        val questionNumber: Int,

        @Schema(description = "질문 내용", example = "지난 2주 동안 불안감을 느낀 적이 있습니까?")
        val questionText: String,

        @Schema(description = "선택한 답변", example = "가끔 그렇다")
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