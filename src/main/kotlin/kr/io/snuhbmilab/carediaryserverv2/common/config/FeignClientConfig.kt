package kr.io.snuhbmilab.carediaryserverv2.common.config

import org.springframework.cloud.openfeign.EnableFeignClients
import org.springframework.context.annotation.Configuration

@Configuration
@EnableFeignClients(basePackages = ["kr.io.snuhbmilab.carediaryserverv2.external"])
class FeignClientConfig