//package net.engineeringdigest.journalApp.Controllers;
//import com.amazonaws.services.s3.AmazonS3;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RequestParam;
//import org.springframework.web.bind.annotation.RestController;
//import software.amazon.awssdk.services.rekognition.RekognitionClient;
//import software.amazon.awssdk.services.rekognition.model.*;
//
//
//@RestController
//@RequestMapping("/api/public/compare")
//public class FaceComparisonController {
//    private final RekognitionClient rekognitionClient;
//    private final String bucketName = "pavanprivateprac.dev";
//    private final AmazonS3 amazonS3;
//
//    public FaceComparisonController(RekognitionClient rekognitionClient, AmazonS3 amazonS3) {
//        this.rekognitionClient = rekognitionClient;
//        this.amazonS3 = amazonS3;
//
//    }
//
//    @GetMapping
//    public String compareFaces(@RequestParam String sourceImage, @RequestParam String targetImage) {
//        try {
//            Image source = Image.builder()
//                    .s3Object(S3Object.builder().bucket(bucketName).name(sourceImage).build())
//                    .build();
//
//            Image target = Image.builder()
//                    .s3Object(S3Object.builder().bucket(bucketName).name(targetImage).build())
//                    .build();
//
//            CompareFacesRequest request = CompareFacesRequest.builder()
//                    .sourceImage(source)
//                    .targetImage(target)
//                    .similarityThreshold(70F) // Adjust as per your requirements
//                    .build();
//
//            CompareFacesResponse response = rekognitionClient.compareFaces(request);
//
//            if (response.faceMatches().isEmpty()) {
//                return "No faces matched";
//            } else {
//                CompareFacesMatch match = response.faceMatches().getFirst();
//                return "Face match found with similarity: " + match.similarity();
//            }
//        } catch (RekognitionException e) {
//            e.printStackTrace();
//            return "Face comparison failed";
//        }
//    }
//}
