package com.training.aigouapi.controller;
import com.training.aigouapi.util.OSSUtils;
import org.apache.tomcat.util.http.fileupload.impl.FileSizeLimitExceededException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

/**
 * @author 十五
 */
@RestController
@CrossOrigin
public class UploadController {
    @RequestMapping("/upload")
    protected ResponseEntity<String> upload(MultipartFile file) throws IOException {
        try {
            OSSUtils.upload(file.getInputStream(), file.getName());
            String url = OSSUtils.getUrl(file.getName());
            return ResponseEntity.ok(url);
        } catch (FileSizeLimitExceededException ex) {
            ex.printStackTrace();
            return ResponseEntity.ok("文件过大");
        }
    }
}