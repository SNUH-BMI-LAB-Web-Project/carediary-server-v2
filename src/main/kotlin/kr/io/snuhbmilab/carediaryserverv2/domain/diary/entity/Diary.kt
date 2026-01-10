package kr.io.snuhbmilab.carediaryserverv2.domain.diary.entity

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.EnumType
import jakarta.persistence.Enumerated
import jakarta.persistence.FetchType
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.JoinColumn
import jakarta.persistence.Lob
import jakarta.persistence.ManyToOne
import jakarta.persistence.Table
import kr.io.snuhbmilab.carediaryserverv2.common.entity.BaseTimeEntity
import kr.io.snuhbmilab.carediaryserverv2.domain.user.entity.User
import java.time.LocalDate
import java.util.UUID

@Entity
@Table(name = "diaries")
class Diary(
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "diary_id", nullable = false)
    val id: UUID? = null,

    @Column(name = "uploader_id", nullable = false)
    val uploaderId: UUID,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "uploader_id", insertable = false, updatable = false)
    val uploader: User? = null,

    @Column(name = "date", nullable = false)
    val date: LocalDate,

    @Lob
    @Column(name = "content", nullable = false, columnDefinition = "TEXT")
    val content: String,

    @Enumerated(EnumType.STRING)
    @Column(name = "emotion", nullable = false)
    val emotion: Emotion,
) : BaseTimeEntity() {

    enum class Emotion {
        HAPPY, LOVE, SAD
    }
}