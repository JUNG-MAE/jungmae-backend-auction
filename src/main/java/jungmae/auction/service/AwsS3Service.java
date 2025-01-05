package jungmae.auction.service;

import com.amazonaws.SdkClientException;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.client.builder.AwsClientBuilder;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.AmazonS3Exception;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AwsS3Service {

    private AmazonS3 s3;


    @Value("${cloud.aws.s3.bucket}")
    private String bucketName;
    @Value("${cloud.aws.region.static}")
    private String region;
    @Value("${cloud.aws.credentials.access-key}")
    private String accessKey;
    @Value("${cloud.aws.credentials.secret-key}")
    private String secretKey;
    @Value("${cloud.aws.s3.endpoint}")
    private String endPoint;


    @PostConstruct  // 의존성 주입 완료 후 자동 실행되도록 설정
    public void init() {
        // AWS S3 클라이언트 초기화
        BasicAWSCredentials awsCredentials = new BasicAWSCredentials(accessKey, secretKey);
        this.s3 = AmazonS3ClientBuilder.standard()
                .withEndpointConfiguration(new AwsClientBuilder.EndpointConfiguration(endPoint, region))
                .withCredentials(new AWSStaticCredentialsProvider(awsCredentials))
                .build();

        // 버킷 생성
        // 이미 해당 이름의 버킷이 생략되어 있다면 넘어감.
        try {
            if (!s3.doesBucketExistV2(bucketName)) {
                s3.createBucket(bucketName);
                System.out.format("Bucket %s has been created.\n", bucketName);
            } else {
                System.out.format("Bucket %s already exists.\n", bucketName);
            }
        } catch (AmazonS3Exception e) {
            System.err.println("Error creating bucket: " + e.getMessage());
        } catch (SdkClientException e) {
            System.err.println("SDK Client error: " + e.getMessage());
        }
    }

    // 경매 등록 데이터의 byte형식으로 되어있는 image를 클라우드 저장소에 저장 후 image값을 Stirng 형태의 저장소 url값으로 재정의 후 반환.
    public List<String> uploadImages(byte[][] images, String folderName) throws IOException {
        List<String> urls = new ArrayList<>();

        for (int i = 0; i < images.length; i++) {
            System.out.println(i + " 번째 이미지 바이트 = " + images[i]);
            if (images[i] == null || images[i].length == 0) {
                continue; // 비어 있는 이미지는 건너뜀
            }
            String fileName = folderName + "/image-" + System.currentTimeMillis() + "-" + i + ".jpg"; // 고유한 키 생성
            String mimeType = "image/jpeg"; // 기본 MIME 타입 설정 (필요 시 동적으로 변경 가능)

            // ByteArrayInputStream을 try-with-resources로 사용하여 자동으로 닫기
            try (ByteArrayInputStream inputStream = new ByteArrayInputStream(images[i])) {
                ObjectMetadata metadata = new ObjectMetadata();
                metadata.setContentLength(images[i].length);
                metadata.setContentType(mimeType); // MIME 타입 설정

                // 이미지 업로드 요청
                PutObjectRequest request = new PutObjectRequest(bucketName, fileName, inputStream, metadata);
                s3.putObject(request); // 클라우드에 이미지 업로드

                // 업로드 후 URL 생성
                String url = s3.getUrl(bucketName, fileName).toString();
                urls.add(url); // URL 리스트에 추가
            } catch (AmazonS3Exception e) {
                e.printStackTrace(); // S3 관련 예외 처리
                // 추가적인 로깅이나 사용자에게 피드백을 주는 코드 추가 가능
            } catch (SdkClientException e) {
                e.printStackTrace(); // SDK 관련 예외 처리
                // 추가적인 로깅이나 사용자에게 피드백을 주는 코드 추가 가능
            }

        }

        return urls; // 모든 URL 반환
    }
}
