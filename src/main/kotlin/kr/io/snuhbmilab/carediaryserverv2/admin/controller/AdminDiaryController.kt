package kr.io.snuhbmilab.carediaryserverv2.admin.controller

import kr.io.snuhbmilab.carediaryserverv2.admin.dto.response.AdminDiaryKeywordResponse
import kr.io.snuhbmilab.carediaryserverv2.admin.dto.response.AdminDiarySdohResponse
import kr.io.snuhbmilab.carediaryserverv2.admin.dto.response.AdminDiaryWelfareServiceResponse
import kr.io.snuhbmilab.carediaryserverv2.admin.facade.AdminDiaryFacade
import kr.io.snuhbmilab.carediaryserverv2.common.dto.CommonResponse
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.util.UUID

@RestController
@RequestMapping("/v1/admin/diaries")
class AdminDiaryController(
    private val adminDiaryFacade: AdminDiaryFacade
) : AdminDiaryApi {
    @GetMapping("/{diaryId}/sdoh")
    override fun findSdoh(@PathVariable diaryId: UUID): CommonResponse<AdminDiarySdohResponse> {
        return CommonResponse.ok(adminDiaryFacade.findSdoh(diaryId))
    }

    @GetMapping("/{diaryId}/keywords")
    override fun findExtractedKeywords(@PathVariable diaryId: UUID): CommonResponse<AdminDiaryKeywordResponse> {
        return CommonResponse.ok(adminDiaryFacade.findExtractedKeywords(diaryId))
    }

    @GetMapping("/{diaryId}/welfare-services")
    override fun findWelfareServices(@PathVariable diaryId: UUID): CommonResponse<AdminDiaryWelfareServiceResponse> {
        return CommonResponse.ok(adminDiaryFacade.findWelfareServices(diaryId))
    }
}