package kr.io.snuhbmilab.carediaryserverv2.domain.diary.repository

import kr.io.snuhbmilab.carediaryserverv2.domain.diary.entity.DiaryWelfareServiceEntity
import kr.io.snuhbmilab.carediaryserverv2.domain.user.entity.User
import org.springframework.data.jpa.repository.JpaRepository

interface DiaryWelfareServiceRepository : JpaRepository<DiaryWelfareServiceEntity, Long> {
    fun findAllByUserAndVisibleIsTrue(user: User): List<DiaryWelfareServiceEntity>
}