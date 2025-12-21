package kr.io.snuhbmilab.carediaryserverv2.domain.diary.service

import kr.io.snuhbmilab.carediaryserverv2.domain.diary.entity.Diary
import kr.io.snuhbmilab.carediaryserverv2.domain.diary.repository.DiaryRepository
import kr.io.snuhbmilab.carediaryserverv2.domain.user.entity.User
import org.springframework.stereotype.Service
import java.time.LocalDate

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
}