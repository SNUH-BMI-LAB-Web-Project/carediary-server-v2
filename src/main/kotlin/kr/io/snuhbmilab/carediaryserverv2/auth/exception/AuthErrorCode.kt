package kr.io.snuhbmilab.carediaryserverv2.auth.exception

import kr.io.snuhbmilab.carediaryserverv2.common.ErrorCode
import org.springframework.http.HttpStatus

enum class AuthErrorCode(
    override val httpStatus: HttpStatus,
    override val message: String,
) : ErrorCode {
    INVALID_ACCESS_TOKEN(HttpStatus.UNAUTHORIZED, "유효하지 않은 AccessToken입니다."),
    EXPIRED_ACCESS_TOKEN(HttpStatus.UNAUTHORIZED, "로그인 유효기간이 만료되었습니다."),
}