package kr.io.snuhbmilab.carediaryserverv2.domain.diary.entity

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.FetchType
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.JoinColumn
import jakarta.persistence.Lob
import jakarta.persistence.OneToOne
import jakarta.persistence.Table
import kr.io.snuhbmilab.carediaryserverv2.common.entity.BaseTimeEntity

@Entity
@Table(name = "diary_analysis_results")
class DiaryAnalysisResult(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "dar_id", nullable = false)
    val id: Long? = null,

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "diary_id", nullable = false)
    val diary: Diary,

    @Lob
    @Column(name = "summary", columnDefinition = "TEXT")
    val summary: String? = null,

    @Lob
    @Column(name = "full_output_text", nullable = false, columnDefinition = "TEXT")
    val fullOutputText: String,
) : BaseTimeEntity()