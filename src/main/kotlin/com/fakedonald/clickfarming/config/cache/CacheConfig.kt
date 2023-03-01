package com.fakedonald.clickfarming.config.cache

import com.fakedonald.clickfarming.config.AliyunOssConfig
import com.github.benmanes.caffeine.cache.Cache
import com.github.benmanes.caffeine.cache.Caffeine
import org.springframework.cache.CacheManager
import org.springframework.cache.annotation.EnableCaching
import org.springframework.cache.caffeine.CaffeineCacheManager
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import java.util.concurrent.TimeUnit

/**
 * @author nathan
 * @date 2023/2/12 01:52
 */

@EnableCaching
@Configuration
class CacheConfig {

    @Bean
    fun cacheManager(): CacheManager {
        val cacheManager = CaffeineCacheManager()
        cacheManager.setCaffeine(caffeineCacheBuilder())
        return cacheManager
    }

    @Bean
    fun caffeineCache(): Cache<Any, Any> {
        return Caffeine.newBuilder()
            .expireAfterWrite(60, TimeUnit.SECONDS)
            .initialCapacity(100)
            .maximumSize(3000)
            .build()
    }

    fun caffeineCacheBuilder(): Caffeine<Any, Any> =
        Caffeine.newBuilder()
            .initialCapacity(100)
            .maximumSize(3000)
            .expireAfterAccess(60, TimeUnit.SECONDS)
            .weakKeys()
            .recordStats()

}