package com.training.aigoushopapi.controller;

import com.baomidou.mybatisplus.core.toolkit.IdWorker;
import com.training.aigoushopapi.util.OSSUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

/**
 * @author 十五
 */
@RestController
@CrossOrigin
@RequestMapping("/upload")
public class UploadController {

    @PostMapping("/fileupload")
    protected ResponseEntity<String> upload(@RequestParam("file") MultipartFile file) throws IOException {
        try {
            // 获取原始文件名和扩展名
            String originalFilename = file.getOriginalFilename();
            if (originalFilename.isEmpty()) {
                return ResponseEntity.badRequest().body("文件名为空");
            }

            String extension = originalFilename.substring(originalFilename.lastIndexOf("."));

            // 使用 MyBatis-Plus 的 IdWorker 生成唯一 ID 作为文件名
            String newFileName = IdWorker.getIdStr() + extension;

            // 上传文件到 OSS，使用新的文件名
            OSSUtils.upload(file.getInputStream(), newFileName);

            // 获取上传后的文件访问 URL
            String url = OSSUtils.getUrl(newFileName);

            // 返回文件 URL
            return ResponseEntity.ok(url);
        } catch (Exception ex) {
            ex.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("上传失败");
        }
    }
}



