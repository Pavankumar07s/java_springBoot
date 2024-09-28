package net.engineeringdigest.journalApp.Service;
import com.amazonaws.HttpMethod;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class S3Service {
    private final AmazonS3 amazonS3;
    private final String bucketName = "pavanprivateprac.dev"; // You can fetch from application.yml as well

    @Autowired
    public S3Service(AmazonS3 amazonS3) {
        this.amazonS3 = amazonS3;
    }

    // 1. Upload a file to S3
    public String uploadFile(MultipartFile file) throws IOException {
        File convertedFile = convertMultipartFileToFile(file);
        String fileName = System.currentTimeMillis() + "_" + file.getOriginalFilename();
        amazonS3.putObject(new PutObjectRequest(bucketName, fileName, convertedFile));
        convertedFile.delete();  // Clean up after uploading
        return fileName;
    }

    // 2. Generate a presigned URL for downloading
    public URL generatePresignedUrl(String fileName) {
        Date expiration = new Date();
        long expTimeMillis = expiration.getTime();
        expTimeMillis += 1000 * 60 * 60; // URL valid for 1 hour
        expiration.setTime(expTimeMillis);

        GeneratePresignedUrlRequest generatePresignedUrlRequest =
                new GeneratePresignedUrlRequest(bucketName, fileName)
                        .withMethod(HttpMethod.GET)
                        .withExpiration(expiration);

        return amazonS3.generatePresignedUrl(generatePresignedUrlRequest);
    }

    // 3. Stream the object directly from S3
    public InputStream downloadFileAsStream(String fileName) {
        S3Object s3Object = amazonS3.getObject(new GetObjectRequest(bucketName, fileName));
        return s3Object.getObjectContent();
    }

    // Helper method to convert MultipartFile to File
    private File convertMultipartFileToFile(MultipartFile file) throws IOException {
        File convertedFile = new File(Objects.requireNonNull(file.getOriginalFilename()));
        try (FileOutputStream fos = new FileOutputStream(convertedFile)) {
            fos.write(file.getBytes());
        }
        return convertedFile;
    }

    // Method to list all objects in the S3 bucket
    public List<String> listAllObjects() {
        ObjectListing objectListing = amazonS3.listObjects(bucketName);
        List<S3ObjectSummary> summaries = objectListing.getObjectSummaries();

        // Map the object summaries to a list of file names
        return summaries.stream()
                .map(S3ObjectSummary::getKey)
                .collect(Collectors.toList());
    }

}
