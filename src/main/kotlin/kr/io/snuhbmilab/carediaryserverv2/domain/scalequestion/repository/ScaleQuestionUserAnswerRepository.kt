package kr.io.snuhbmilab.carediaryserverv2.domain.scalequestion.repository

import kr.io.snuhbmilab.carediaryserverv2.domain.scalequestion.entity.ScaleQuestionUserAnswer
import org.springframework.data.jpa.repository.JpaRepository

interface ScaleQuestionUserAnswerRepository : JpaRepository<ScaleQuestionUserAnswer, Long> {
}