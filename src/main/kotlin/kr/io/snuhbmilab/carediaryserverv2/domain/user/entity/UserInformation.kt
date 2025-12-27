package kr.io.snuhbmilab.carediaryserverv2.domain.user.entity

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.EnumType
import jakarta.persistence.Enumerated
import jakarta.persistence.FetchType
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.JoinColumn
import jakarta.persistence.OneToOne
import jakarta.persistence.Table
import org.hibernate.annotations.OnDelete
import org.hibernate.annotations.OnDeleteAction
import java.time.LocalDate
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

    @Enumerated(EnumType.STRING)
    @Column(name = "medical_coverage")
    val medicalCoverage: MedicalCoverage? = null,

    @Column(name = "special_case_registered", columnDefinition = "TINYINT(1)")
    val specialCaseRegistered: Boolean? = null,

    @Column(name = "special_case_registered_date")
    val specialCaseRegisteredDate: LocalDate? = null,

    @Column(name = "disability_registered", columnDefinition = "TINYINT(1)")
    val disabilityRegistered: Boolean? = null,

    @Enumerated(EnumType.STRING)
    @Column(name = "disability_status")
    val disabilityStatus: DisabilityStatus? = null,

    @Column(name = "disability_type")
    val disabilityType: String? = null,

    @Enumerated(EnumType.STRING)
    @Column(name = "disability_severity")
    val disabilitySeverity: DisabilitySeverity? = null,

    @Column(name = "social_welfare_service_labels")
    val socialWelfareServiceLabels: String? = null
) {
    enum class MedicalCoverage {
        //건강보험
        HEALTH_INSURANCE,
        //의료급여 1종
        MEDICAL_AID_1,
        //의료급여 2종
        MEDICAL_AID_2,
        //차상위 1종
        NEAR_POOR_1,
        //차상위 2종
        NEAR_POOR_2
    }

    enum class DisabilityStatus {
        REGISTERED,
        IN_PROGRESS,
        NOT_REGISTERED
    }

    enum class DisabilitySeverity {
        SEVERE, NOT_SEVERE
    }
}