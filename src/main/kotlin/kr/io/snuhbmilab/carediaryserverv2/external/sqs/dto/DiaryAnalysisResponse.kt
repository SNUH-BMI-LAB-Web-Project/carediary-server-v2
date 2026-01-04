package kr.io.snuhbmilab.carediaryserverv2.external.sqs.dto

import com.fasterxml.jackson.annotation.JsonProperty
import java.util.UUID

data class DiaryAnalysisResponse(
    @JsonProperty("diary_id")
    val diaryId: UUID,
    val items: List<DiaryAnalysisItem>
)

data class DiaryAnalysisItem(
    @JsonProperty("element_no")
    val elementNo: Int,
    @JsonProperty("major_cat")
    val majorCat: String,
    @JsonProperty("middle_cat")
    val middleCat: String,
    @JsonProperty("sub_cat")
    val subCat: String,
    @JsonProperty("sign_code")
    val signCode: String,
    @JsonProperty("type_label")
    val typeLabel: String?,
    val severity: Int,
    val duration: Int?,
    val coping: Int?,
    val recommendation: String,
    val evidences: List<String>
)
