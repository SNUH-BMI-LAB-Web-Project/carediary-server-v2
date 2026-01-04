package kr.io.snuhbmilab.carediaryserverv2.common.dto

import com.fasterxml.jackson.annotation.JsonInclude
import kr.io.snuhbmilab.carediaryserverv2.common.ErrorCode
import kr.io.snuhbmilab.carediaryserverv2.common.GlobalErrorCode
import kr.io.snuhbmilab.carediaryserverv2.common.SUCCESS_CODE
import kr.io.snuhbmilab.carediaryserverv2.common.SUCCESS_MESSAGE
import kr.io.snuhbmilab.carediaryserverv2.common.SuccessCode
import kr.io.snuhbmilab.carediaryserverv2.common.SuccessMessage
import org.springframework.core.MethodParameter
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.http.converter.HttpMessageConverter
import org.springframework.http.server.ServerHttpRequest
import org.springframework.http.server.ServerHttpResponse
import org.springframework.web.bind.annotation.RestControllerAdvice
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice

data class CommonResponse<T>(
    val status: Int,
    val code: String,
    val message: String,
    @JsonInclude(value = JsonInclude.Include.NON_NULL)
    val data: T? = null
) {
    companion object {
        @JvmStatic
        fun <T> success(successCode: SuccessCode, data: T? = null): CommonResponse<T> =
            CommonResponse(successCode.httpStatus.value(), SUCCESS_CODE, SUCCESS_MESSAGE, data)

        @JvmStatic
        fun <T> ok(successMessage: SuccessMessage, data: T? = null): CommonResponse<T> =
            CommonResponse(SuccessCode.OK.httpStatus.value(), successMessage.name, successMessage.message, data)

        @JvmStatic
        fun <T> ok(data: T? = null): CommonResponse<T> = success(SuccessCode.OK, data)

        @JvmStatic
        fun error(errorCode: ErrorCode): CommonResponse<Unit> =
            CommonResponse(errorCode.httpStatus.value(), errorCode.name, errorCode.message)

        @JvmStatic
        fun error(exception: Exception, httpStatus: HttpStatus): CommonResponse<Unit> =
            CommonResponse(httpStatus.value(), httpStatus.name, exception.message ?: exception.toString())

        @JvmStatic
        fun error(exception: Exception): CommonResponse<Unit> {
            val internalErrorCode = GlobalErrorCode.INTERNAL_SERVER_ERROR

            return CommonResponse(
                internalErrorCode.httpStatus.value(),
                internalErrorCode.name,
                "${exception.javaClass.simpleName}: ${exception.message}"
            )
        }
    }
}

@RestControllerAdvice
class CommonResponseBodyAdvice : ResponseBodyAdvice<Any> {
    override fun supports(returnType: MethodParameter, converterType: Class<out HttpMessageConverter<*>?>): Boolean =
        true

    override fun beforeBodyWrite(
        body: Any?, returnType: MethodParameter, selectedContentType: MediaType,
        selectedConverterType: Class<out HttpMessageConverter<*>?>,
        request: ServerHttpRequest, response: ServerHttpResponse
    ): Any? {
        if (body is CommonResponse<*>) {
            response.setStatusCode(HttpStatus.valueOf(body.status))
        }

        return body
    }
}