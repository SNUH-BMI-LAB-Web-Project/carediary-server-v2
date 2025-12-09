package kr.io.snuhbmilab.carediaryserverv2.common

import org.springframework.http.HttpStatus

const val SUCCESS_MESSAGE = "요청에 성공했습니다."
const val SUCCESS_CODE = "SUCCESS"

enum class SuccessCode(
    val httpStatus: HttpStatus,
) {
    OK(HttpStatus.OK),
    CREATED(HttpStatus.CREATED),
    NO_CONTENT(HttpStatus.NO_CONTENT),
}

interface ErrorCode {
    val httpStatus: HttpStatus
    val message: String
    val name: String
}

enum class GlobalErrorCode(
    override val httpStatus: HttpStatus,
    override val message: String,
) : ErrorCode {
    INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "응답 처리 중, 예외가 발생했습니다."),
}