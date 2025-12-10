package kr.io.snuhbmilab.carediaryserverv2.auth.security

import kr.io.snuhbmilab.carediaryserverv2.domain.user.service.UserService
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.User
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.stereotype.Service
import java.util.UUID

typealias SpringSecurityUser = User

@Service
class UserDetailsServiceImpl(
    private val userService: UserService,
) : UserDetailsService {

    override fun loadUserByUsername(userId: String): UserDetails {
        val user = userService.findById(UUID.fromString(userId))

        return SpringSecurityUser(userId, null, listOf(SimpleGrantedAuthority(user.role.authority)))
    }
}