package kr.io.snuhbmilab.carediaryserverv2.common.exception

import kr.io.snuhbmilab.carediaryserverv2.common.dto.CommonResponse
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice

@RestControllerAdvice
class GlobalExceptionHandler {
    @ExceptionHandler(BusinessException::class)
    fun handleBusinessException(businessException: BusinessException): CommonResponse<Unit> {
        val errorCode = businessException.errorCode

        return CommonResponse.error(errorCode)
    }

    @ExceptionHandler(Exception::class)
    fun handleException(exception: Exception): CommonResponse<Unit> = CommonResponse.error(exception)
}
