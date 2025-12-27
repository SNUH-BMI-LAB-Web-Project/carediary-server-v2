package kr.io.snuhbmilab.carediaryserverv2.domain.user.controller

import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.Parameter
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.responses.ApiResponses
import io.swagger.v3.oas.annotations.security.SecurityRequirement
import io.swagger.v3.oas.annotations.tags.Tag
import kr.io.snuhbmilab.carediaryserverv2.common.annotation.UserId
import kr.io.snuhbmilab.carediaryserverv2.common.dto.CommonResponse
import kr.io.snuhbmilab.carediaryserverv2.domain.user.dto.request.UserRegisterRequest
import kr.io.snuhbmilab.carediaryserverv2.domain.user.dto.response.CurrentUserResponse
import kr.io.snuhbmilab.carediaryserverv2.domain.user.dto.response.UserRegisterResponse
import org.springframework.web.bind.annotation.RequestBody
import java.util.UUID

@Tag(name = "User", description = "사용자 관련 API")
@SecurityRequirement(name = "JWT")
interface UserApi {

    @Operation(
        summary = "회원가입",
        description = "OAuth2 인증 후 사용자 상세 정보를 등록합니다. 이름, 역할, 성별, 생년월일, 주소 및 의료 관련 정보를 포함합니다."
    )
    @ApiResponses(
        value = [
            ApiResponse(responseCode = "200", description = "사용자 등록 성공"),
            ApiResponse(responseCode = "400", description = "잘못된 요청"),
            ApiResponse(responseCode = "401", description = "인증 실패"),
            ApiResponse(responseCode = "409", description = "이미 등록된 사용자")
        ]
    )
    fun register(
        @Parameter(hidden = true) @UserId userId: UUID,
        @RequestBody request: UserRegisterRequest
    ): CommonResponse<UserRegisterResponse>

    @Operation(
        summary = "현재 사용자 정보 조회",
        description = "현재 로그인한 사용자의 상세 정보를 조회합니다."
    )
    @ApiResponses(
        value = [
            ApiResponse(responseCode = "200", description = "사용자 정보 조회 성공"),
            ApiResponse(responseCode = "401", description = "인증 실패"),
            ApiResponse(responseCode = "404", description = "사용자를 찾을 수 없음")
        ]
    )
    fun getMe(
        @Parameter(hidden = true) @UserId userId: UUID
    ): CommonResponse<CurrentUserResponse>
}