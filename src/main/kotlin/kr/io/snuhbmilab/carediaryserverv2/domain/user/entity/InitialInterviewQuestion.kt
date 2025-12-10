package kr.io.snuhbmilab.carediaryserverv2.domain.user.entity

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.Lob
import jakarta.persistence.Table

@Entity
@Table(name = "initial_interview_questions")
class InitialInterviewQuestion(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "iiq_id", nullable = false)
    val id: Long? = null,

    @Lob
    @Column(name = "content", nullable = false, columnDefinition = "TEXT")
    val content: String,
)