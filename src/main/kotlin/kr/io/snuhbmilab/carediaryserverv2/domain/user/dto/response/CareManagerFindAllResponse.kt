package kr.io.snuhbmilab.carediaryserverv2.domain.user.dto.response

import io.swagger.v3.oas.annotations.media.Schema

@Schema(description = "담당 관리자 목록 응답")
data class CareManagerFindAllResponse(
    @Schema(description = "담당 관리자 목록")
    val careManagers: List<CareManagerResponse>,
)
