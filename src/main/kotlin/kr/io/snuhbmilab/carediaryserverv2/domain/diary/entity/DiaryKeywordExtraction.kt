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

@Entity
@Table(name = "diary_keyword_extractions")
class DiaryKeywordExtraction(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "dke_id", nullable = false)
    val id: Long? = null,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    val user: User,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "diary_id", nullable = false)
    val diary: Diary,

    @Enumerated(EnumType.STRING)
    @Column(name = "keyword_group", nullable = false)
    val keywordGroup: KeywordGroup,

    @Column(name = "keyword", nullable = false)
    val keyword: String,

    @Column(name = "keyword_code", length = 8)
    val keywordCode: String? = null,

    @Lob
    @Column(name = "evidences", columnDefinition = "TEXT")
    val evidences: String? = null,
) : BaseTimeEntity() {

    enum class KeywordGroup {
        LIFE_CYCLE, HOUSEHOLD_STATUS, INTERESTS
    }
}