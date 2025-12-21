package kr.io.snuhbmilab.carediaryserverv2.domain.diary.repository

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
    fun countByUploaderIdAndEmotion(uploaderId: UUID, emotion: Diary.Emotion): Int

    @Query("SELECT d.date FROM Diary d WHERE d.uploader.id = :uploaderId AND d.date BETWEEN :startDate AND :endDate")
    fun findAllDateByUploaderIdAndDateBetween(
        uploaderId: UUID,
        startDate: LocalDate,
        endDate: LocalDate
    ): List<LocalDate>
}