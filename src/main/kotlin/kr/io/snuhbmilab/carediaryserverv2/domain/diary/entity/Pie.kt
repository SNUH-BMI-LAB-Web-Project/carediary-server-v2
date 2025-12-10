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
import kr.io.snuhbmilab.carediaryserverv2.common.entity.BaseTimeEntity

@Entity
@Table(name = "pie")
class Pie(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "pie_id", nullable = false)
    val id: Long? = null,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "dar_id", nullable = false)
    val diaryAnalysisResult: DiaryAnalysisResult,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "diary_id", nullable = false)
    val diary: Diary,

    @Column(name = "element_no", nullable = false)
    val elementNo: Short,

    @Column(name = "major_cat")
    val majorCat: String? = null,

    @Column(name = "middle_cat")
    val middleCat: String? = null,

    @Column(name = "sub_cat")
    val subCat: String? = null,

    @Column(name = "sign_code")
    val signCode: String? = null,

    @Column(name = "type_label")
    val typeLabel: String? = null,

    @Column(name = "severity")
    val severity: Short? = null,

    @Column(name = "duration")
    val duration: Short? = null,

    @Column(name = "coping")
    val coping: Short? = null,

    @Lob
    @Column(name = "recommendation", columnDefinition = "TEXT")
    val recommendation: String? = null,

    @Lob
    @Column(name = "evidences", nullable = false, columnDefinition = "TEXT")
    val evidences: String,
) : BaseTimeEntity()