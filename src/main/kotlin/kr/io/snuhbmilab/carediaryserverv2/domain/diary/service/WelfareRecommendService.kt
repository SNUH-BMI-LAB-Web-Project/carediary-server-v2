package kr.io.snuhbmilab.carediaryserverv2.domain.diary.service

import kr.io.snuhbmilab.carediaryserverv2.domain.diary.repository.DiaryWelfareServiceRepository
import kr.io.snuhbmilab.carediaryserverv2.domain.user.entity.User
import org.springframework.stereotype.Service


@Service
class WelfareRecommendService(
    private val diaryWelfareServiceRepository: DiaryWelfareServiceRepository
) {

    fun findAllVisible(user: User) = diaryWelfareServiceRepository.findAllByUserAndVisibleIsTrue(user)
}