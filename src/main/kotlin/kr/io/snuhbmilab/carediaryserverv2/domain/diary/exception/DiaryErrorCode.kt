package kr.io.snuhbmilab.carediaryserverv2.domain.diary.exception

import kr.io.snuhbmilab.carediaryserverv2.common.ErrorCode
import org.springframework.http.HttpStatus

enum class DiaryErrorCode(
    override val httpStatus: HttpStatus,
    override val message: String,
) : ErrorCode {
    DIARY_NOT_FOUND(HttpStatus.NOT_FOUND, "일기 정보를 찾을 수 없습니다."),
    DIARY_ACCESS_DENIED(HttpStatus.FORBIDDEN, "일기 정보에 접근할 수 없습니다."),
    WELFARE_SERVICE_NOT_FOUND(HttpStatus.NOT_FOUND, "복지 서비스 정보를 찾을 수 없습니다."),
}
