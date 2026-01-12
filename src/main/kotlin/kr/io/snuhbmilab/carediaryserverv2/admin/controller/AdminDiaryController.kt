package kr.io.snuhbmilab.carediaryserverv2.admin.controller

import kr.io.snuhbmilab.carediaryserverv2.admin.dto.response.AdminDiaryFindAllResponse
import kr.io.snuhbmilab.carediaryserverv2.admin.dto.response.AdminDiarySdohResponse
import kr.io.snuhbmilab.carediaryserverv2.admin.facade.AdminDiaryFacade
import kr.io.snuhbmilab.carediaryserverv2.common.dto.CommonResponse
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import java.time.LocalDate
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

    @GetMapping
    override fun findAllByUserIdAndDate(
        @RequestParam userId: UUID,
        @RequestParam date: LocalDate
    ): CommonResponse<AdminDiaryFindAllResponse> {
        return CommonResponse.ok(adminDiaryFacade.findAllByUserIdAndDate(userId, date))
    }

    @PostMapping("/{diaryId}/welfare-services/{welfareServiceId}/visible")
    override fun updateWelfareServiceVisible(
        @PathVariable diaryId: UUID,
        @PathVariable welfareServiceId: Long
    ): CommonResponse<Unit> {
        adminDiaryFacade.updateWelfareServiceVisible(diaryId, welfareServiceId)
        return CommonResponse.ok()
    }

    @DeleteMapping("/{diaryId}/welfare-services/{welfareServiceId}/visible")
    override fun updateWelfareServiceInvisible(
        @PathVariable diaryId: UUID,
        @PathVariable welfareServiceId: Long
    ): CommonResponse<Unit> {
        adminDiaryFacade.updateWelfareServiceInvisible(diaryId, welfareServiceId)
        return CommonResponse.ok()
    }
}