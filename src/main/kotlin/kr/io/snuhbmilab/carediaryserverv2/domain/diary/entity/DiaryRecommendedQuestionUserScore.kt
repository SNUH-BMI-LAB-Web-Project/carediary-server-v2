package kr.io.snuhbmilab.carediaryserverv2.domain.diary.entity

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.FetchType
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.JoinColumn
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

    @Column(name = "drq_id", nullable = false)
    val questionId: Long,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "drq_id", updatable = false, insertable = false)
    var question: DiaryRecommendedQuestion? = null,

    @Column(name = "score", nullable = false)
    val score: Int,
)