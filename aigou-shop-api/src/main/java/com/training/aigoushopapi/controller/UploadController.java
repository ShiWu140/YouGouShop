package com.training.aigoushopapi.controller;

import com.training.aigoushopapi.util.OSSUtils;
import org.apache.tomcat.util.http.fileupload.impl.FileSizeLimitExceededException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

/**
 * @author 十五
 */
@RestController
@CrossOrigin
public class UploadController {

    @RequestMapping(value = "/upload", method = RequestMethod.POST)
    protected ResponseEntity<String> upload(@RequestParam("file") MultipartFile file) throws IOException {
        try {
            // 上传文件到 OSS
            OSSUtils.upload(file.getInputStream(), file.getOriginalFilename());
            String url = OSSUtils.getUrl(file.getOriginalFilename());
            return ResponseEntity.ok(url);
        } catch (FileSizeLimitExceededException ex) {
            ex.printStackTrace();
            return ResponseEntity.ok("文件过大");
        }
    }
}

