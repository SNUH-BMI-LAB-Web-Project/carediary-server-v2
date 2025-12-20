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
import jakarta.persistence.UniqueConstraint
import kr.io.snuhbmilab.carediaryserverv2.domain.user.entity.User

@Entity
@Table(
    name = "scale_question_user_answers",
    uniqueConstraints = [
        UniqueConstraint(
            name = "uk_user_scale_question_term",
            columnNames = ["user_id", "scale_question_id", "term_count"]
        )
    ]
)
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

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "scale_question_id", updatable = false, insertable = false)
    var question: ScaleQuestion? = null,

    @Column(name = "term_count", nullable = false)
    val termCount: Int  = 0,

    @Column(name = "user_answer", nullable = false)
    val userAnswer: Int,
)