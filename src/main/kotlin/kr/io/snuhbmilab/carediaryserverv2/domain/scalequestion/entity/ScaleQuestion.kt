package kr.io.snuhbmilab.carediaryserverv2.domain.scalequestion.entity

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.Lob
import jakarta.persistence.Table

@Entity
@Table(name = "scale_questions")
class ScaleQuestion(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "scale_question_id", nullable = false)
    val id: Long? = null,

    @Lob
    @Column(name = "content", nullable = false, columnDefinition = "TEXT")
    val content: String,
)