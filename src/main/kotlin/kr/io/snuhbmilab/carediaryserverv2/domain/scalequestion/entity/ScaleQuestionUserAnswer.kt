package kr.io.snuhbmilab.carediaryserverv2.domain.scalequestion.entity

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
import kr.io.snuhbmilab.carediaryserverv2.domain.user.entity.User

@Entity
@Table(name = "scale_question_user_answers")
class ScaleQuestionUserAnswer(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "squa_id", nullable = false)
    val id: Long? = null,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    val user: User,

    @Column(name = "scale_question_id", nullable = false)
    val scaleQuestionId: Long,

    @ManyToOne(fetch = FetchType.LAZY, optional = true)
    @JoinColumn(name = "scale_question_id", updatable = false, insertable = false)
    var question: ScaleQuestion? = null,

    @Column(name = "term_count", nullable = false)
    val termCount: Int  = 0,

    @Lob
    @Column(name = "user_answer", columnDefinition = "TEXT", nullable = false)
    val userAnswer: Int,
)