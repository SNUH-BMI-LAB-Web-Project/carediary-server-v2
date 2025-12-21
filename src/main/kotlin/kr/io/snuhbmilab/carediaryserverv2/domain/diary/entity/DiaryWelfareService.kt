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
@Table(name = "diary_welfare_services")
class DiaryWelfareService(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "dws_id", nullable = false)
    val id: Long? = null,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    val user: User,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "diary_id", nullable = false)
    val diary: Diary,

    @Enumerated(EnumType.STRING)
    @Column(name = "service_scope", nullable = false)
    val serviceScope: ServiceScope,

    @Column(name = "serv_id", nullable = false, length = 64)
    val serviceId: String,

    @Column(name = "serv_nm", nullable = false, length = 512)
    val serviceName: String,

    @Lob
    @Column(name = "serv_dtl_link", nullable = false, columnDefinition = "TEXT")
    val serviceDetailLink: String,

    @Lob
    @Column(name = "serv_dgst", columnDefinition = "TEXT")
    val serviceDigest: String? = null,

    @Column(name = "admin_lv1_name")
    val adminLevel1Name: String? = null,

    @Column(name = "admin_lv2_name")
    val adminLevel2Name: String? = null,

    @Lob
    @Column(name = "matched_life_cycle_keywords", columnDefinition = "TEXT")
    val matchedLifeCycleKeywords: String? = null,

    @Lob
    @Column(name = "matched_household_status_keywords", columnDefinition = "TEXT")
    val matchedHouseholdStatusKeywords: String? = null,

    @Lob
    @Column(name = "matched_interest_keywords", columnDefinition = "TEXT")
    val matchedInterestKeywords: String? = null,
) : BaseTimeEntity() {

    enum class ServiceScope {
        LOCAL, NATIONAL
    }
}