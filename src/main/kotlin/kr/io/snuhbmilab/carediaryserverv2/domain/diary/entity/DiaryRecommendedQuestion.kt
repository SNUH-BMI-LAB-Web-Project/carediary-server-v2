package kr.io.snuhbmilab.carediaryserverv2.domain.diary.entity

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.Lob
import jakarta.persistence.Table

@Entity
@Table(name = "diary_recommended_questions")
class DiaryRecommendedQuestion(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "drq_id", nullable = false)
    val id: Long? = null,

    @Lob
    @Column(name = "content", nullable = false, columnDefinition = "TEXT")
    val content: String,
)