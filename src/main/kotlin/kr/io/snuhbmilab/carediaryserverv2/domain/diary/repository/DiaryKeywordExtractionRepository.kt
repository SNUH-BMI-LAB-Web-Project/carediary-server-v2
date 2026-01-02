package kr.io.snuhbmilab.carediaryserverv2.domain.diary.repository

import kr.io.snuhbmilab.carediaryserverv2.domain.diary.entity.DiaryKeywordExtraction
import org.springframework.data.jpa.repository.JpaRepository
import java.util.UUID

interface DiaryKeywordExtractionRepository : JpaRepository<DiaryKeywordExtraction, Long> {
    fun findAllByDiaryId(diaryId: UUID): List<DiaryKeywordExtraction>
}