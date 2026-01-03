package kr.io.snuhbmilab.carediaryserverv2.domain.diary.repository

import kr.io.snuhbmilab.carediaryserverv2.domain.diary.entity.DiaryAnalysisResult
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import java.time.LocalDateTime

interface DiaryAnalysisResultRepository : JpaRepository<DiaryAnalysisResult, Long> {
    @Query("SELECT COUNT(dar) FROM DiaryAnalysisResult dar WHERE dar.createdAt >= :startDateTime")
    fun countByCreatedAtAfter(startDateTime: LocalDateTime): Long
}