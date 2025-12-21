package kr.io.snuhbmilab.carediaryserverv2.domain.diary.entity

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.FetchType
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.JoinColumn
import jakarta.persistence.Lob
import jakarta.persistence.ManyToOne
import jakarta.persistence.Table

@Entity
@Table(name = "diary_recommended_questions_user_scores")
class DiaryRecommendedQuestionUserScore(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "drqus_id", nullable = false)
    val id: Long? = null,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "diary_id", nullable = false)
    val diary: Diary,

    @Lob
    @Column(name = "question_text", nullable = false, columnDefinition = "TEXT")
    val questionText: String,

    @Column(name = "score", nullable = false)
    val score: Int,
)