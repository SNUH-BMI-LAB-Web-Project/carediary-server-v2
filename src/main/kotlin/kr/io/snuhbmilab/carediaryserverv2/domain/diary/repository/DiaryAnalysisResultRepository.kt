package kr.io.snuhbmilab.carediaryserverv2.domain.diary.repository

import kr.io.snuhbmilab.carediaryserverv2.domain.diary.dto.query.UserCountQueryResult
import kr.io.snuhbmilab.carediaryserverv2.domain.diary.entity.DiaryAnalysisResult
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import java.time.LocalDateTime
import java.util.UUID

interface DiaryAnalysisResultRepository : JpaRepository<DiaryAnalysisResult, Long> {
    @Query("SELECT COUNT(dar) FROM DiaryAnalysisResult dar WHERE dar.createdAt >= :startDateTime")
    fun countByCreatedAtAfterOrEqual(startDateTime: LocalDateTime): Long

    @Query("SELECT COUNT(dar) FROM DiaryAnalysisResult dar WHERE dar.diary.uploader.id = :uploaderId")
    fun countByUploaderId(uploaderId: UUID): Long

    @Query("""
        SELECT dar.diary.uploader.id as uploaderId, COUNT(dar) as count
        FROM DiaryAnalysisResult dar
        WHERE dar.diary.uploader.id IN :uploaderIds
        GROUP BY dar.diary.uploader.id
    """)
    fun countByUploaderIdIn(uploaderIds: List<UUID>): List<UserCountQueryResult>
}