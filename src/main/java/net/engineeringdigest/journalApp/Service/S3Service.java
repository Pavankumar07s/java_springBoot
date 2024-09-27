package net.engineeringdigest.journalApp.Service;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.GetObjectRequest;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.services.s3.model.S3Object;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Service
public class S3Service {
    private final AmazonS3 amazonS3;
    private final String bucketName = "pavanprivateprac.dev"; // You can fetch from application.yml as well

    @Autowired
    public S3Service(AmazonS3 amazonS3) {
        this.amazonS3 = amazonS3;
    }

    public String uploadFile(MultipartFile file) throws IOException {
        File convertedFile = convertMultipartFileToFile(file);
        String fileName = System.currentTimeMillis() + "_" + file.getOriginalFilename();
        amazonS3.putObject(new PutObjectRequest(bucketName, fileName, convertedFile));
        convertedFile.delete();  // Cleanup after upload
        return fileName;
    }

    public File downloadFile(String fileName) throws IOException {
        S3Object s3Object = amazonS3.getObject(new GetObjectRequest(bucketName, fileName));
        InputStream inputStream = s3Object.getObjectContent();

        // Save the object to a temporary local file (or you can return the InputStream directly)
        Path tempFilePath = Paths.get(System.getProperty("java.io.tmpdir"), fileName);
        Files.copy(inputStream, tempFilePath);
        return tempFilePath.toFile();
    }
    private File convertMultipartFileToFile(MultipartFile file) throws IOException {
        File convertedFile = new File(file.getOriginalFilename());
        try (FileOutputStream fos = new FileOutputStream(convertedFile)) {
            fos.write(file.getBytes());
        }
        return convertedFile;
    }
}
