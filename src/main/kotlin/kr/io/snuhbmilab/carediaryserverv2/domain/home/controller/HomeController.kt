package kr.io.snuhbmilab.carediaryserverv2.domain.home.controller

import kr.io.snuhbmilab.carediaryserverv2.common.annotation.UserId
import kr.io.snuhbmilab.carediaryserverv2.common.dto.CommonResponse
import kr.io.snuhbmilab.carediaryserverv2.domain.home.dto.response.HomeResponse
import kr.io.snuhbmilab.carediaryserverv2.domain.home.facade.HomeFacade
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.util.UUID

@RestController
@RequestMapping("/v1/home")
class HomeController(
    private val homeFacade: HomeFacade
) {

    @GetMapping
    fun getHome(@UserId userId: UUID): CommonResponse<HomeResponse> {
        return CommonResponse.ok(homeFacade.getHome(userId))
    }
}