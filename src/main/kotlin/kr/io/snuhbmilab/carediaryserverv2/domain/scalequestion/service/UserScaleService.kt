package kr.io.snuhbmilab.carediaryserverv2.domain.scalequestion.service

import kr.io.snuhbmilab.carediaryserverv2.domain.scalequestion.constants.ScaleCategory
import kr.io.snuhbmilab.carediaryserverv2.domain.scalequestion.entity.UserScale
import kr.io.snuhbmilab.carediaryserverv2.domain.scalequestion.repository.UserScaleRepository
import kr.io.snuhbmilab.carediaryserverv2.domain.user.entity.User
import org.springframework.stereotype.Service

@Service
class UserScaleService(
    private val userScaleRepository: UserScaleRepository
) {

    fun append(user: User, scaleCategory: ScaleCategory, score: Int): UserScale = userScaleRepository.save(
        UserScale(
            user = user,
            scaleCategory = scaleCategory,
            score = score,
            termCount = user.scaleQuestionTermCount
        )
    )

    fun findAll(user: User): List<UserScale> = userScaleRepository.findAllByUserId(user.id!!)

    fun findAllByTermCount(user: User, count: Int): List<UserScale> = userScaleRepository.findAllByUserIdAndTermCount(user.id!!, count)
}