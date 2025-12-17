package kr.io.snuhbmilab.carediaryserverv2.domain.user.entity

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.FetchType
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.JoinColumn
import jakarta.persistence.OneToOne
import jakarta.persistence.Table
import org.hibernate.annotations.OnDelete
import org.hibernate.annotations.OnDeleteAction
import java.time.YearMonth

@Entity
@Table(name = "user_information")
class UserInformation(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_information_id", nullable = false)
    val id: Long? = null,

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    val user: User,

    @Column(name = "education_before_onset")
    val educationBeforeOnset: String? = null,

    @Column(name = "previous_diagnosis")
    val previousDiagnosis: String? = null,

    @Column(name = "diagnosis_ym")
    val diagnosisYearMonth: YearMonth? = null,

    @Column(name = "diagnosis_hospital")
    val diagnosisHospital: String? = null,

    @Column(name = "chief_complaint")
    val chiefComplaint: String? = null,

    @Column(name = "current_hospital")
    val currentHospital: String? = null,

    @Column(name = "current_residence")
    val currentResidence: String? = null,
)