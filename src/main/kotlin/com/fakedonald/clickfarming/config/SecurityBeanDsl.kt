package com.fakedonald.clickfarming.config

import com.nimbusds.jose.jwk.JWK
import com.nimbusds.jose.jwk.JWKSet
import com.nimbusds.jose.jwk.RSAKey
import com.nimbusds.jose.jwk.source.ImmutableJWKSet
import com.nimbusds.jose.jwk.source.JWKSource
import com.nimbusds.jose.proc.SecurityContext
import org.springframework.context.support.beans
import org.springframework.http.HttpMethod
import org.springframework.security.config.Customizer
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configurers.ExceptionHandlingConfigurer
import org.springframework.security.config.annotation.web.configurers.SessionManagementConfigurer
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder
import org.springframework.security.oauth2.jwt.NimbusJwtEncoder
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter
import org.springframework.security.oauth2.server.resource.authentication.JwtGrantedAuthoritiesConverter
import org.springframework.security.oauth2.server.resource.web.BearerTokenAuthenticationEntryPoint
import org.springframework.security.oauth2.server.resource.web.access.BearerTokenAccessDeniedHandler
import org.springframework.web.servlet.config.annotation.CorsRegistry
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer
import java.security.interfaces.RSAPrivateKey
import java.security.interfaces.RSAPublicKey


/**
 * @author nathan
 * @date 2023/2/19 22:33
 *
 * spring security bean definition
 */

val securityContext = beans {
    // security
    bean { BCryptPasswordEncoder() }
    bean {
        val jwk: JWK = RSAKey.Builder(env.getRequiredProperty("jwt.public.key", RSAPublicKey::class.java))
            .privateKey(env.getRequiredProperty("jwt.private.key", RSAPrivateKey::class.java)).build()
        val jwks: JWKSource<SecurityContext> = ImmutableJWKSet(JWKSet(jwk))
        NimbusJwtEncoder(jwks)
    }
    bean {
        NimbusJwtDecoder.withPublicKey(env.getRequiredProperty("jwt.public.key", RSAPublicKey::class.java)).build()
    }
    bean(::jwtAuthenticationConverter)
    // Spring security config
    bean {
        val http = ref<HttpSecurity>()
        http.authorizeHttpRequests {
            it.requestMatchers("/auth/**").permitAll()
            it.requestMatchers(HttpMethod.OPTIONS).permitAll()
            it.anyRequest().authenticated()
        }
            .csrf().disable()
            .cors().disable()
            .httpBasic(Customizer.withDefaults())
            .sessionManagement { session: SessionManagementConfigurer<HttpSecurity> ->
                session.sessionCreationPolicy(
                    SessionCreationPolicy.STATELESS
                )
            }.exceptionHandling { exceptions: ExceptionHandlingConfigurer<HttpSecurity> ->
                exceptions
                    .authenticationEntryPoint(BearerTokenAuthenticationEntryPoint())
                    .accessDeniedHandler(BearerTokenAccessDeniedHandler())
            }.oauth2ResourceServer().jwt().jwtAuthenticationConverter(jwtAuthenticationConverter())
        http.build()
    }
    // enable cors
    bean {
        object : WebMvcConfigurer {
            override fun addCorsMappings(registry: CorsRegistry) {
                registry.addMapping("/**")
                    .allowedOriginPatterns("*")
                    .allowedHeaders("*")
                    .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")
            }
        }
    }
}

fun jwtAuthenticationConverter(): JwtAuthenticationConverter {
    val grantedAuthoritiesConverter = JwtGrantedAuthoritiesConverter()
    grantedAuthoritiesConverter.setAuthorityPrefix("")
    grantedAuthoritiesConverter.setAuthoritiesClaimName("scope")
    val jwtAuthenticationConverter = JwtAuthenticationConverter()
    jwtAuthenticationConverter.setJwtGrantedAuthoritiesConverter(grantedAuthoritiesConverter)
    return jwtAuthenticationConverter
}