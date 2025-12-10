package kr.io.snuhbmilab.carediaryserverv2.domain.user.exception

import kr.io.snuhbmilab.carediaryserverv2.common.ErrorCode
import org.springframework.http.HttpStatus

enum class UserErrorCode(
    override val httpStatus: HttpStatus,
    override val message: String,
) : ErrorCode {
    USER_NOT_FOUND(HttpStatus.NOT_FOUND, "존재하지 않는 사용자 정보입니다."),
}