package kr.io.snuhbmilab.carediaryserverv2.common.config

import feign.Logger
import feign.Request
import feign.Retryer
import feign.codec.ErrorDecoder
import kr.io.snuhbmilab.carediaryserverv2.external.exception.ExternalException
import org.springframework.cloud.openfeign.EnableFeignClients
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import java.util.concurrent.TimeUnit

@Configuration
@EnableFeignClients(basePackages = ["kr.io.snuhbmilab.carediaryserverv2.external"])
class FeignClientConfig {

    @Bean
    fun feignRequestOptions(): Request.Options = Request.Options(5, TimeUnit.SECONDS, 60, TimeUnit.SECONDS, true)

    @Bean
    fun feignRetryer(): Retryer = Retryer.NEVER_RETRY

    @Bean
    fun feignErrorDecoder(): ErrorDecoder = ErrorDecoder { _, response ->
        throw ExternalException("외부 API 호출 실패: ${response.status()} - ${response.reason()}")
    }

    @Bean
    fun feignLoggerLevel(): Logger.Level = Logger.Level.BASIC
}