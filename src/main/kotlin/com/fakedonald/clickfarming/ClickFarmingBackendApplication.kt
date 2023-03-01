package com.fakedonald.clickfarming

import com.fakedonald.clickfarming.config.redisContext
import com.fakedonald.clickfarming.config.securityContext
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.support.beans
import org.springframework.core.convert.converter.Converter
import org.springframework.data.jpa.repository.config.EnableJpaAuditing
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.web.cors.CorsConfiguration
import org.springframework.web.cors.UrlBasedCorsConfigurationSource
import org.springframework.web.filter.CorsFilter
import java.sql.SQLException
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

@SpringBootApplication
@Configuration
@EnableJpaAuditing
@EnableWebSecurity
@EnableMethodSecurity(prePostEnabled = true)
class KotlinAdminBackendApplication

// Converters
class LocalDateTimeConverter : Converter<String, LocalDateTime> {
    override fun convert(source: String): LocalDateTime {
        return LocalDateTime.parse(source, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))
    }
}

fun main(args: Array<String>) {
    runApplication<KotlinAdminBackendApplication>(*args) {
        addInitializers(
            securityContext,
            redisContext,
            beans {
                bean(::LocalDateTimeConverter)
            }
        )
    }
}