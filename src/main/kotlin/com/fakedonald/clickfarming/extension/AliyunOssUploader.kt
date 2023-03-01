package com.fakedonald.clickfarming.extension

import com.aliyun.oss.OSS
import com.aliyun.oss.OSSClientBuilder
import com.aliyun.oss.OSSException
import com.fakedonald.clickfarming.config.AliyunOssConfig
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import org.springframework.web.multipart.MultipartFile
import java.util.*

/**
 * @author nathan
 * @date 2023/2/15 16:55
 */

@Component
class AliyunOssUploader {

    @Autowired
    private lateinit var ossConfig: AliyunOssConfig

    private lateinit var ossClient: OSS

    /**
     * 上传文件, 返回文件链接
     */
    fun uploadFile(file: MultipartFile): String? {
        ossClient = OSSClientBuilder().build(ossConfig.endpoint, ossConfig.accessKey, ossConfig.accessSecret)
        // generate file name
        val key = "${generateRandomCode(10)}${System.currentTimeMillis()}.${
            file.originalFilename?.substring(
                file.originalFilename!!.lastIndexOf(".") + 1
            )
        }"
        try {
            ossClient.putObject(
                ossConfig.bucket,
                key,
                file.inputStream
            )

            var fileUrl = ossClient.generatePresignedUrl(ossConfig.bucket, key, Date()).toString()
            fileUrl = fileUrl.substring(0, fileUrl.indexOf("?"))
            return fileUrl
        } catch (oe: OSSException) {
            throw CustomException(oe.errorMessage)
        } finally {
            ossClient.shutdown()
        }
    }
}