package kr.io.snuhbmilab.carediaryserverv2.domain.diary.service

import kr.io.snuhbmilab.carediaryserverv2.common.utils.toDateRange
import kr.io.snuhbmilab.carediaryserverv2.common.exception.BusinessException
import kr.io.snuhbmilab.carediaryserverv2.domain.diary.entity.Diary
import kr.io.snuhbmilab.carediaryserverv2.domain.diary.exception.DiaryErrorCode
import kr.io.snuhbmilab.carediaryserverv2.domain.diary.repository.DiaryRepository
import kr.io.snuhbmilab.carediaryserverv2.domain.user.entity.User
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import java.time.LocalDate
import java.time.Year
import java.time.YearMonth
import java.util.UUID

@Service
class DiaryService(
    private val diaryRepository: DiaryRepository
) {
    fun create(user: User, date: LocalDate, content: String, emotion: Diary.Emotion) =
        diaryRepository.save(
            Diary(
                uploader = user,
                date = date,
                content = content,
                emotion = emotion
            )
        )

    fun findAllByUserAndPeriod(user: User, startDate: LocalDate?, endDate: LocalDate?): List<Diary> {
        if (startDate == null && endDate == null) {
            return diaryRepository.findAllByUploaderOrderByDateDesc(user)
        }

        if ((startDate != null && endDate == null) || (startDate?.isEqual(endDate) == true)) {
            return diaryRepository.findAllByUploaderAndDateOrderByCreatedAtDesc(user, startDate)
        }

        if (startDate != null && endDate != null && startDate.isBefore(endDate)) {
            return diaryRepository.findAllByUploaderAndDateBetweenOrderByDateDesc(user, startDate, endDate)
        }

        return emptyList()
    }

    fun findById(diaryId: UUID): Diary {
        return diaryRepository.findByIdOrNull(diaryId)
            ?: throw BusinessException(DiaryErrorCode.DIARY_NOT_FOUND)
    }

    fun countMonthly(userId: UUID, yearMonth: YearMonth): Int {
        val dateRange = yearMonth.toDateRange()
        return diaryRepository
            .countByUploaderIdAndDateBetween(userId, dateRange.start, dateRange.endInclusive)
            .toInt()
    }

    fun countYearly(userId: UUID, year: Year): Int {
        val dateRange = year.toDateRange()
        return diaryRepository
            .countByUploaderIdAndDateBetween(userId, dateRange.start, dateRange.endInclusive)
            .toInt()
    }

    fun countByEmotion(userId: UUID): Map<Diary.Emotion, Int> {
        val results = diaryRepository.countByUploaderIdGroupByEmotion(userId)
        return results.associate { it.emotion to it.count.toInt() }
    }

    fun findDatesMonthly(userId: UUID, yearMonth: YearMonth): List<LocalDate> {
        val dateRange = yearMonth.toDateRange()
        return diaryRepository.findDistinctDatesByUploaderIdAndDateBetween(userId, dateRange.start, dateRange.endInclusive)
    }
}