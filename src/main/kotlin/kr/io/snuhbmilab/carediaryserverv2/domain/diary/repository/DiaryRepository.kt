package kr.io.snuhbmilab.carediaryserverv2.domain.diary.repository

import kr.io.snuhbmilab.carediaryserverv2.domain.diary.dto.query.EmotionCountQueryResult
import kr.io.snuhbmilab.carediaryserverv2.domain.diary.dto.query.UserCountQueryResult
import kr.io.snuhbmilab.carediaryserverv2.domain.diary.entity.Diary
import kr.io.snuhbmilab.carediaryserverv2.domain.user.entity.User
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import java.time.LocalDate
import java.util.UUID

interface DiaryRepository : JpaRepository<Diary, UUID> {
    fun findAllByUploaderOrderByDateDesc(user: User): List<Diary>
    fun findAllByUploaderAndDateOrderByCreatedAtDesc(user: User, startDate: LocalDate): List<Diary>
    fun findAllByUploaderAndDateBetweenOrderByDateDesc(
        user: User,
        startDate: LocalDate,
        endDate: LocalDate
    ): List<Diary>

    fun countByUploaderIdAndDateBetween(uploaderId: UUID, startDate: LocalDate, endDate: LocalDate): Long

    @Query("""
        SELECT d.emotion as emotion, COUNT(d) as count
        FROM Diary d
        WHERE d.uploader.id = :uploaderId
        GROUP BY d.emotion
    """)
    fun countByUploaderIdGroupByEmotion(uploaderId: UUID): List<EmotionCountQueryResult>

    @Query(
        """
            SELECT DISTINCT d.date
            FROM Diary d
            WHERE d.uploader.id = :uploaderId
            AND d.date BETWEEN :startDate AND :endDate
            ORDER BY d.date
        """
    )
    fun findDistinctDatesByUploaderIdAndDateBetween(
        uploaderId: UUID,
        startDate: LocalDate,
        endDate: LocalDate
    ): List<LocalDate>

    fun existsByUploaderId(userId: UUID): Boolean

    fun countByUploaderId(uploaderId: UUID): Long

    fun findAllByUploaderIdAndDateOrderByCreatedAtDesc(uploaderId: UUID, date: LocalDate): List<Diary>

    @Query("""
        SELECT d.uploader.id as uploaderId, COUNT(d) as count
        FROM Diary d
        WHERE d.uploader.id IN :uploaderIds
        GROUP BY d.uploader.id
    """)
    fun countByUploaderIdIn(uploaderIds: List<UUID>): List<UserCountQueryResult>
}