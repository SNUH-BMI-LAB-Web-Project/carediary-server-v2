package kr.io.snuhbmilab.carediaryserverv2.domain.diary.repository

import kr.io.snuhbmilab.carediaryserverv2.domain.diary.entity.Pie
import org.springframework.data.jpa.repository.JpaRepository
import java.util.UUID

interface PieRepository : JpaRepository<Pie, Long> {
    fun findAllByDiaryId(diaryId: UUID): List<Pie>

    fun findAllByDiaryUploaderId(userId: UUID): List<Pie>
}