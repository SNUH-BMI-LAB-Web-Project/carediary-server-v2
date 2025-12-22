package kr.io.snuhbmilab.carediaryserverv2.domain.scalequestion.entity

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.EnumType
import jakarta.persistence.Enumerated
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

    @Column(name = "question_no", nullable = false)
    val questionNumber: Int,

    @Enumerated(EnumType.STRING)
    @Column(name = "scale_category", nullable = false)
    val scaleCategory: ScaleCategory,

    @Lob
    @Column(nullable = false, columnDefinition = "TEXT")
    val options: String,

    @Lob
    @Column(name = "content", nullable = false, columnDefinition = "TEXT")
    val content: String,
) {

    enum class ScaleCategory {
        ANXIETY, DEPRESSION, ANGER
    }
}