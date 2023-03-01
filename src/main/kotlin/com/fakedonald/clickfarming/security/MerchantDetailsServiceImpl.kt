package com.fakedonald.clickfarming.security

import com.fakedonald.clickfarming.domain.AuthRequest
import com.fakedonald.clickfarming.domain.merchant.Merchant
import com.fakedonald.clickfarming.extension.CustomException
import com.fakedonald.clickfarming.extension.equal
import com.fakedonald.clickfarming.extension.notFound
import com.fakedonald.clickfarming.repository.merchant.MerchantRepository
import org.springframework.beans.factory.annotation.Value
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.oauth2.jwt.JwtClaimsSet
import org.springframework.security.oauth2.jwt.JwtEncoder
import org.springframework.security.oauth2.jwt.JwtEncoderParameters
import org.springframework.stereotype.Service
import java.time.Instant
import java.time.temporal.ChronoUnit

/**
 * @author nathan
 * @date 2023/2/25 20:03
 */
@Service
class MerchantDetailsServiceImpl(
    private val merchantRepository: MerchantRepository,
    private val jwtEncoder: JwtEncoder,
    private val passwordEncoder: BCryptPasswordEncoder
) : UserDetailsService {

    @Value("\${jwt.issuer}")
    private val issuer: String? = null

    @Value("\${jwt.expiry}")
    private val expiry: Long? = null

    override fun loadUserByUsername(username: String?): UserDetails {
        return merchantRepository.findOne(Merchant::username.equal(username)).notFound()
    }


    fun authenticate(authRequest: AuthRequest): String {
        val userDetails = loadUserByUsername(authRequest.username) as Merchant
        // verify password
        val now = Instant.now()
        if (passwordEncoder.matches(authRequest.password, userDetails.password)) {
            val claims = JwtClaimsSet
                .builder()
                .issuer(this.issuer)
                .issuedAt(now)
                .expiresAt(expiry?.let { now.plus(it, ChronoUnit.HOURS) })
                .claims {
                    it["userId"] = userDetails.id
                    it["username"] = userDetails.username
                }
                .build()
            // @formatter:on
            return jwtEncoder.encode(JwtEncoderParameters.from(claims)).tokenValue
        }

        throw CustomException("用户名或密码错误!")
    }
}