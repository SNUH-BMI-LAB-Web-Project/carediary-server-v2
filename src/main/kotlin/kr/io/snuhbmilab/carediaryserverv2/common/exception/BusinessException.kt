package kr.io.snuhbmilab.carediaryserverv2.common.exception

import kr.io.snuhbmilab.carediaryserverv2.common.ErrorCode
import kr.io.snuhbmilab.carediaryserverv2.common.GlobalErrorCode
import java.lang.RuntimeException

class BusinessException(
    val errorCode: ErrorCode = GlobalErrorCode.INTERNAL_SERVER_ERROR,
    override val cause: Throwable? = null,
) : RuntimeException(errorCode.message, cause)