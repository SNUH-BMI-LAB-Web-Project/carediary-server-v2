package kr.io.snuhbmilab.carediaryserverv2.domain.diary.repository

import kr.io.snuhbmilab.carediaryserverv2.domain.diary.entity.Diary
import org.springframework.data.jpa.repository.JpaRepository
import java.util.UUID

interface DiaryRepository : JpaRepository<Diary, UUID> {

}