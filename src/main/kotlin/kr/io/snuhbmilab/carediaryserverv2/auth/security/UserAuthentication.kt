package kr.io.snuhbmilab.carediaryserverv2.auth.security

import kr.io.snuhbmilab.carediaryserverv2.common.constants.Role
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.authority.SimpleGrantedAuthority
import java.util.UUID

class UserAuthentication(
    userId: UUID,
    role: Role,
) : UsernamePasswordAuthenticationToken(
    userId,
    null,
    listOf(SimpleGrantedAuthority(role.authority))
)