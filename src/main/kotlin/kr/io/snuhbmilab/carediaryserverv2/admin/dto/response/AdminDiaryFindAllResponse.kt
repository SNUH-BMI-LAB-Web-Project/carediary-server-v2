package kr.io.snuhbmilab.carediaryserverv2.admin.dto.response

import io.swagger.v3.oas.annotations.media.Schema
import kr.io.snuhbmilab.carediaryserverv2.common.utils.parseListFromDBText
import kr.io.snuhbmilab.carediaryserverv2.domain.diary.entity.Diary
import kr.io.snuhbmilab.carediaryserverv2.domain.diary.entity.DiaryKeywordExtraction
import kr.io.snuhbmilab.carediaryserverv2.domain.diary.entity.DiaryRecommendedQuestionUserScore
import kr.io.snuhbmilab.carediaryserverv2.domain.diary.entity.DiaryWelfareServiceEntity
import java.time.LocalDate
import java.time.LocalDateTime
import java.util.UUID

@Schema(description = "관리자 일기 목록 조회 응답")
data class AdminDiaryFindAllResponse(
    @Schema(description = "일기 목록")
    val diaries: List<AdminDiaryDto>
) {
    @Schema(description = "추천 질문 점수")
    data class RecommendedQuestionUserScoreDto(
        @Schema(description = "질문 텍스트", example = "오늘 하루 얼마나 활동적이었나요?")
        val questionText: String,

        @Schema(description = "사용자가 입력한 점수", example = "7")
        val score: Int
    ) {
        companion object {
            @JvmStatic
            fun from(entity: DiaryRecommendedQuestionUserScore) = RecommendedQuestionUserScoreDto(
                questionText = entity.questionText,
                score = entity.score
            )
        }
    }

    @Schema(description = "일기 키워드 정보")
    data class DiaryKeywordDto(
        @Schema(description = "키워드 추출 ID", example = "1")
        val keywordExtractionId: Long,

        @Schema(
            description = "키워드 그룹 (LIFE_CYCLE: 생애주기, HOUSEHOLD_STATUS: 가구상황, INTERESTS: 관심사)",
            example = "LIFE_CYCLE"
        )
        val keywordGroup: DiaryKeywordExtraction.KeywordGroup,

        @Schema(description = "키워드", example = "청년")
        val keyword: String,

        @Schema(description = "키워드 코드", example = "LC001")
        val keywordCode: String?,

        @Schema(description = "키워드 추출 근거 문장 목록", example = "[\"취업 준비를 하고 있다\", \"청년 주거 지원이 필요하다\"]")
        val evidences: List<String>
    ) {
        companion object {
            @JvmStatic
            fun from(entity: DiaryKeywordExtraction) = DiaryKeywordDto(
                keywordExtractionId = entity.id!!,
                keywordGroup = entity.keywordGroup,
                keyword = entity.keyword,
                keywordCode = entity.keywordCode,
                evidences = entity.evidences?.parseListFromDBText() ?: emptyList()
            )
        }
    }

    @Schema(description = "일기 복지 서비스 정보")
    data class DiaryWelfareServiceDto(
        @Schema(description = "복지 서비스 ID", example = "1")
        val welfareServiceId: Long,

        @Schema(description = "서비스 범위 (CENTRAL: 중앙정부, LOCAL: 지방정부)", example = "CENTRAL")
        val serviceScope: DiaryWelfareServiceEntity.ServiceScope,

        @Schema(description = "서비스 고유 ID", example = "WS2024001")
        val serviceId: String,

        @Schema(description = "서비스명", example = "정신건강복지센터 상담 서비스")
        val serviceName: String,

        @Schema(description = "서비스 상세 링크", example = "https://www.bokjiro.go.kr/service/12345")
        val serviceDetailLink: String,

        @Schema(description = "서비스 요약 설명", example = "정신건강 상담 및 사례관리 서비스를 제공합니다.")
        val serviceDigest: String?,

        @Schema(description = "행정구역 1단계 (시/도)", example = "서울특별시")
        val adminLevel1Name: String?,

        @Schema(description = "행정구역 2단계 (시/군/구)", example = "강남구")
        val adminLevel2Name: String?,

        @Schema(description = "매칭된 생애주기 키워드 목록", example = "[\"청년\", \"중장년\"]")
        val matchedLifeCycleKeywords: List<String>,

        @Schema(description = "매칭된 가구상황 키워드 목록", example = "[\"1인 가구\", \"저소득층\"]")
        val matchedHouseholdStatusKeywords: List<String>,

        @Schema(description = "매칭된 관심사 키워드 목록", example = "[\"정신건강\", \"심리상담\"]")
        val matchedInterestKeywords: List<String>,

        @Schema(description = "사용자에게 표시 여부", example = "true")
        val visible: Boolean
    ) {
        companion object {
            @JvmStatic
            fun from(entity: DiaryWelfareServiceEntity) = DiaryWelfareServiceDto(
                welfareServiceId = entity.id!!,
                serviceScope = entity.serviceScope,
                serviceId = entity.serviceId,
                serviceName = entity.serviceName,
                serviceDetailLink = entity.serviceDetailLink,
                serviceDigest = entity.serviceDigest,
                adminLevel1Name = entity.adminLevel1Name,
                adminLevel2Name = entity.adminLevel2Name,
                matchedLifeCycleKeywords = entity.matchedLifeCycleKeywords?.parseListFromDBText() ?: emptyList(),
                matchedHouseholdStatusKeywords = entity.matchedHouseholdStatusKeywords?.parseListFromDBText()
                    ?: emptyList(),
                matchedInterestKeywords = entity.matchedInterestKeywords?.parseListFromDBText() ?: emptyList(),
                visible = entity.visible
            )
        }
    }

    @Schema(description = "일기 정보")
    data class AdminDiaryDto(
        @Schema(description = "일기 ID", example = "550e8400-e29b-41d4-a716-446655440000")
        val diaryId: UUID,

        @Schema(description = "작성자 ID", example = "550e8400-e29b-41d4-a716-446655440001")
        val uploaderId: UUID,

        @Schema(description = "일기 작성 날짜", example = "2024-12-25")
        val date: LocalDate,

        @Schema(description = "일기 내용", example = "오늘은 날씨가 좋아서 산책을 했다.")
        val content: String,

        @Schema(description = "감정 상태", example = "HAPPY")
        val emotion: Diary.Emotion,

        @Schema(description = "생성 일시")
        val createdAt: LocalDateTime,

        @Schema(description = "추천 질문에 대한 사용자 점수 목록")
        val questionScores: List<RecommendedQuestionUserScoreDto>,

        val extractedKeywords: List<DiaryKeywordDto>,

        val welfareServices: List<DiaryWelfareServiceDto>,
    ) {
        companion object {
            @JvmStatic
            fun of(
                diary: Diary,
                questionScores: List<DiaryRecommendedQuestionUserScore>,
                keywordExtractions: List<DiaryKeywordExtraction>,
                welfareServices: List<DiaryWelfareServiceEntity>
            ) = AdminDiaryDto(
                diaryId = diary.id!!,
                uploaderId = diary.uploaderId,
                date = diary.date,
                content = diary.content,
                emotion = diary.emotion,
                createdAt = diary.createdAt,
                questionScores = questionScores.map { RecommendedQuestionUserScoreDto.from(it) },
                extractedKeywords = keywordExtractions.map { DiaryKeywordDto.from(it) },
                welfareServices = welfareServices.map { DiaryWelfareServiceDto.from(it) }
            )
        }
    }

    companion object {
        @JvmStatic
        fun of(
            diaries: List<Diary>,
            questionScoresMap: Map<UUID, List<DiaryRecommendedQuestionUserScore>>,
            keywordExtractions: Map<UUID, List<DiaryKeywordExtraction>>,
            welfareServices: Map<UUID, List<DiaryWelfareServiceEntity>>
        ) = AdminDiaryFindAllResponse(
            diaries = diaries.map { diary ->
                AdminDiaryDto.of(
                    diary,
                    questionScoresMap[diary.id] ?: emptyList(),
                    keywordExtractions[diary.id] ?: emptyList(),
                    welfareServices[diary.id] ?: emptyList(),
                )
            }
        )
    }
}