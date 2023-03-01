package com.fakedonald.clickfarming.config

import org.springframework.context.support.beans
import org.springframework.data.redis.cache.RedisCacheConfiguration
import org.springframework.data.redis.connection.RedisStandaloneConfiguration
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory
import org.springframework.data.redis.core.RedisTemplate
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer
import org.springframework.data.redis.serializer.StringRedisSerializer
import java.time.Duration

/**
 * @author nathan
 * @date 2023/2/19 23:12
 *
 * Redis bean definition
 */

val redisContext = beans {
    val host = env.getRequiredProperty("spring.data.redis.host")
    val dataBase = env.getRequiredProperty("spring.data.redis.database", Int::class.java)
    val port = env.getRequiredProperty("spring.data.redis.port", Int::class.java)
    val password = env.getRequiredProperty("spring.data.redis.password")

    bean { GenericJackson2JsonRedisSerializer() }
    bean { StringRedisSerializer() }
    bean {
        val redisStandaloneConfiguration = RedisStandaloneConfiguration()
        redisStandaloneConfiguration.database = dataBase
        redisStandaloneConfiguration.setPassword(password)
        redisStandaloneConfiguration.port = port
        redisStandaloneConfiguration.hostName = host
        LettuceConnectionFactory(redisStandaloneConfiguration)
    }

    bean {
        val redisTemplate: RedisTemplate<String, Any> = RedisTemplate<String, Any>()
        redisTemplate.setConnectionFactory(ref<LettuceConnectionFactory>())
        redisTemplate.isEnableDefaultSerializer = false
        redisTemplate.keySerializer = ref<StringRedisSerializer>()
        redisTemplate.valueSerializer = ref<GenericJackson2JsonRedisSerializer>()
        redisTemplate.afterPropertiesSet()
        return@bean redisTemplate
    }

    bean {
        val configuration = RedisCacheConfiguration.defaultCacheConfig()
        configuration.entryTtl(
            Duration.ofHours(6)
        )
    }
}