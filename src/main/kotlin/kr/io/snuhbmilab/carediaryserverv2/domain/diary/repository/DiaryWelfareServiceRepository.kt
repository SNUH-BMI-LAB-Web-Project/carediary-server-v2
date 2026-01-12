package kr.io.snuhbmilab.carediaryserverv2.domain.diary.repository

import kr.io.snuhbmilab.carediaryserverv2.domain.diary.entity.DiaryWelfareServiceEntity
import kr.io.snuhbmilab.carediaryserverv2.domain.user.entity.User
import org.springframework.data.jpa.repository.JpaRepository
import java.util.UUID

interface DiaryWelfareServiceRepository : JpaRepository<DiaryWelfareServiceEntity, Long> {
    fun findAllByUserAndVisibleIsTrue(user: User): List<DiaryWelfareServiceEntity>
    fun findAllByDiaryId(diaryId: UUID): List<DiaryWelfareServiceEntity>
    fun findTop3ByUserAndVisibleIsTrueOrderByCreatedAtDesc(user: User): List<DiaryWelfareServiceEntity>
    fun findByDiaryIdAndId(diaryId: UUID, id: Long): DiaryWelfareServiceEntity?
    fun findAllByDiaryIdIn(diaryIds: List<UUID>): List<DiaryWelfareServiceEntity>
}