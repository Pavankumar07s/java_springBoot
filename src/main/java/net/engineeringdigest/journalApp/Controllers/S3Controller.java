package net.engineeringdigest.journalApp.Controllers;
import net.engineeringdigest.journalApp.Service.S3Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.nio.file.Files;
import java.util.List;

@RestController
@RequestMapping("/api/public/files")
public class S3Controller {

    @Autowired
    private final S3Service s3Service;

    public S3Controller(S3Service s3Service) {
        this.s3Service = s3Service;
    }

    // 1. Upload endpoint
    @PostMapping("/upload")
    public ResponseEntity<String> uploadFile(@RequestParam("file") MultipartFile file) {
        try {
            String fileName = s3Service.uploadFile(file);
            return ResponseEntity.status(HttpStatus.OK).body("File uploaded successfully: " + fileName);
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("File upload failed: " + e.getMessage());
        }
    }

    // 2. Generate presigned URL for downloading
    @GetMapping("/download-url/{fileName}")
    public ResponseEntity<String> generateDownloadUrl(@PathVariable String fileName) {
        URL presignedUrl = s3Service.generatePresignedUrl(fileName);
        return ResponseEntity.status(HttpStatus.OK).body(presignedUrl.toString());
    }

    // 3. Download and stream file content from S3
    @GetMapping("/download-stream/{fileName}")
    public ResponseEntity<byte[]> downloadFileAsStream(@PathVariable String fileName) {
        try {
            InputStream inputStream = s3Service.downloadFileAsStream(fileName);
            byte[] fileContent = inputStream.readAllBytes();

            return ResponseEntity.ok()
                    .header("Content-Disposition", "attachment; filename=\"" + fileName + "\"")
                    .body(fileContent);
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }


    @GetMapping("/list")
    public ResponseEntity<List<String>> listAllObjects() {
        List<String> objectKeys = s3Service.listAllObjects();
        return ResponseEntity.status(HttpStatus.OK).body(objectKeys);
    }


}