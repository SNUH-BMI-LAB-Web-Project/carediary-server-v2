package kr.io.snuhbmilab.carediaryserverv2.domain.diary.repository

import kr.io.snuhbmilab.carediaryserverv2.domain.diary.entity.Diary
import kr.io.snuhbmilab.carediaryserverv2.domain.user.entity.User
import org.springframework.data.jpa.repository.JpaRepository
import java.time.LocalDate
import java.util.UUID

interface DiaryRepository : JpaRepository<Diary, UUID> {
    fun findAllByUploader(user: User): List<Diary>
    fun findAllByUploaderAndDate(user: User, startDate: LocalDate): List<Diary>
    fun findAllByUploaderAndDateBetween(user: User, startDate: LocalDate, endDate: LocalDate): List<Diary>
}