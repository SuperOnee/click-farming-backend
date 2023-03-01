package com.fakedonald.clickfarming.config

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.context.annotation.Configuration

/**
 * @author nathan
 * @date 2023/2/15 16:56
 */

@Configuration
@ConfigurationProperties(prefix = "aliyun.oss")
data class AliyunOssConfig(
    var endpoint: String = "",

    var accessKey: String = "",

    var accessSecret: String = "",

    var bucket: String = "",
)