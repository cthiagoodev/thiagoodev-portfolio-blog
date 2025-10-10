package br.com.thiagoodev.portfolio.infrastructure.services.storage;

import io.minio.BucketExistsArgs;
import io.minio.MakeBucketArgs;
import io.minio.MinioClient;
import io.minio.PutObjectArgs;
import io.minio.errors.MinioException;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

@Service
public class StorageService {
    @Value("${minio.bucket.name}")
    private String bucketName;
    @Value("${storage.uri}")
    private String storageUri;
    private final MinioClient client;

    public StorageService(MinioClient client) {
        this.client = client;
    }

    @PostConstruct
    private void init() {
        try {
            BucketExistsArgs bucketExistsArgs = BucketExistsArgs
                    .builder()
                    .bucket(this.bucketName)
                    .build();
            boolean bucketExists = client.bucketExists(bucketExistsArgs);

            if (!bucketExists) {
                MakeBucketArgs bucketArgs = MakeBucketArgs
                        .builder()
                        .bucket(this.bucketName)
                        .build();
                client.makeBucket(bucketArgs);
            }
        } catch (Exception e) {
            throw new RuntimeException("Could not initialize MinIO bucket: " + e.getMessage(), e);
        }
    }

    public void upload(String objectName, InputStream inputStream, String contentType) {
        try {
            PutObjectArgs args = PutObjectArgs
                    .builder()
                    .bucket(this.bucketName)
                    .object(objectName)
                    .stream(inputStream, -1, 5 * 1024 * 1024)
                    .contentType(contentType)
                    .build();

            this.client.putObject(args);
        } catch (MinioException | InvalidKeyException | IOException | NoSuchAlgorithmException e) {
            throw new RuntimeException("Error during upload to MinIO: " + e.getMessage(), e);
        }
    }

    public String storageUrlResolver(String objectName) {
        URI uri = URI.create(
            this.storageUri + "/" +
            this.bucketName + "/" +
            objectName
        );
        return uri.normalize().toString();
    }
}