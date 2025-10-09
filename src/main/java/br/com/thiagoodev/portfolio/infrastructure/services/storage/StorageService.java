package br.com.thiagoodev.portfolio.infrastructure.services.storage;

import io.minio.BucketExistsArgs;
import io.minio.MakeBucketArgs;
import io.minio.MinioClient;
import io.minio.PutObjectArgs;
import io.minio.errors.MinioException;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

@Slf4j
@Service
public class StorageService {
    @Value("${minio.bucket.name}")
    private String bucketName;
    private final MinioClient client;

    public StorageService(MinioClient client) {
        this.client = client;
    }

    @PostConstruct
    private void init() {
        try {
            boolean bucketExists = client.bucketExists(BucketExistsArgs.builder().bucket(bucketName).build());
            if (!bucketExists) {
                log.info("Bucket '{}' not found. Creating...", bucketName);
                MakeBucketArgs bucketArgs = MakeBucketArgs
                        .builder()
                        .bucket(bucketName)
                        .build();
                client.makeBucket(bucketArgs);
                log.info("Bucket '{}' created successfully.", bucketName);
            }
        } catch (Exception e) {
            throw new RuntimeException("Could not initialize MinIO bucket: " + e.getMessage(), e);
        }
    }

    public void upload(String objectName, InputStream inputStream, String contentType) {
        log.info("Starting upload for object '{}' in bucket '{}'", objectName, bucketName);
        try {
            PutObjectArgs args = PutObjectArgs
                    .builder()
                    .bucket(this.bucketName)
                    .object(objectName)
                    .stream(inputStream, -1, 5 * 1024 * 1024)
                    .contentType(contentType)
                    .build();

            this.client.putObject(args);
            log.info("Upload for object '{}' completed successfully.", objectName);
        } catch (MinioException | InvalidKeyException | IOException | NoSuchAlgorithmException e) {
            throw new RuntimeException("Error during upload to MinIO: " + e.getMessage(), e);
        }
    }
}