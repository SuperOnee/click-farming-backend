package com.fakedonald.clickfarming.contorller

import com.fakedonald.clickfarming.extension.AliyunOssUploader
import com.fakedonald.clickfarming.extension.Response
import com.fakedonald.clickfarming.extension.toJson
import org.springframework.web.bind.annotation.*
import org.springframework.web.multipart.MultipartFile

/**
 * @author nathan
 * @date 2023/2/15 18:50
 *
 */
@RestController
@RequestMapping("/upload")
class UploadController(val ossUploader: AliyunOssUploader) {

    /**
     * 图片上传到阿里OSS
     */
    @PostMapping
    suspend fun save(@RequestParam("file") file: MultipartFile) = ossUploader.uploadFile(file)?.toJson()
}