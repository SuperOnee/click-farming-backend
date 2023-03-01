package com.fakedonald.clickfarming.cache

import org.springframework.data.redis.core.RedisTemplate
import org.springframework.stereotype.Component
import java.util.concurrent.TimeUnit

/**
 * @author nathan
 * @date 2023/2/17 22:32
 */

@Suppress("UNCHECKED_CAST")
@Component
class GlobalCache(val redisTemplate: RedisTemplate<String, Any>) {

    /**
     * 根据指定的Key删除缓存
     */
    fun del(vararg keys: String) {
        keys.forEach {
            redisTemplate.delete(it)
        }
    }

    /**
     * 设置缓存
     */
    fun set(key: String, value: Any) = redisTemplate.opsForValue().set(key, value)

    /**
     * 设置缓存, 携带过期时间
     */
    fun set(key: String, value: Any, timeout: Long, unit: TimeUnit) =
        redisTemplate.opsForValue().set(key, value, timeout, unit)

    /**
     * 根据指定的Key获取数据
     */
    fun <T> get(key: String): T? {
        val value = redisTemplate.opsForValue()[key]
        value?.let {
            return value as T
        }

        return null
    }
}