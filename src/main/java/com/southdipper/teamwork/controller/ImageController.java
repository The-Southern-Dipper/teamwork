package com.southdipper.teamwork.controller;

import com.southdipper.teamwork.pojo.Result;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.PathResource;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.UUID;

@CrossOrigin(origins = "*")
@RestController
public class ImageController {
    @Value("${file.upload.path}")
    private String fileSavePath;
    @Value("${file.coming.path}")
    private String fileComingPath;
    @PostMapping("/upload")
    public Result upload(@RequestParam("image") MultipartFile images, HttpServletRequest request) {
        try {
            String root = System.getProperty("user.dir");
            File dest = new File(root + fileSavePath);
            if (!dest.exists()) {
                dest.mkdirs();
            }
            String fileName = images.getOriginalFilename();
            fileName = fileName.substring(fileName.lastIndexOf("."));
            String s = UUID.randomUUID().toString().replace("-", "").toUpperCase();
            fileName = s + fileName;
            images.transferTo(new File(dest, fileName));
            return Result.success(fileComingPath + fileName);
        }
        catch (Exception e) {
            e.printStackTrace();
            return Result.error(e.getMessage());
        }
    }
    @GetMapping("/images/{filename}")
    public ResponseEntity<byte[]> get(@PathVariable("filename") String fileName, HttpServletResponse response) throws IOException {
        String filePath = System.getProperty("user.dir") + fileSavePath + fileName;
        Resource resource = new PathResource(filePath);
        try (InputStream inputStream = resource.getInputStream()) {
            byte[] buffer = inputStream.readAllBytes();
            String contentType = Files.probeContentType(Paths.get(resource.getURI()));
            if(contentType == null) {
                contentType = MediaType.IMAGE_JPEG_VALUE;
            }
            return ResponseEntity.ok()
                    .contentType(MediaType.parseMediaType(contentType))
                    .body(buffer);
        }
    }
}
