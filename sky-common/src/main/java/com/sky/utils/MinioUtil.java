package com.sky.utils;


import io.minio.BucketExistsArgs;
import io.minio.MakeBucketArgs;
import io.minio.MinioClient;
import io.minio.PutObjectArgs;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import java.io.ByteArrayInputStream;

@Data
@AllArgsConstructor
@Slf4j
public class MinioUtil {
    private String endpoint;
    private String accessKey;
    private String secretKey;
    private String bucketName;
    /**
     * 文件上传
     *
     * @param bytes
     * @param objectName
     * @return
     */
    public String upload(byte[] bytes, String objectName) {
                    try {
                        // 创建MinioClient实例
                        // 设置使用AWS签名版本4
//                        System.setProperty("com.amazonaws.services.s3.enableV4", "true");
                        MinioClient minioClient = MinioClient.builder()
                                .endpoint(endpoint)
                                .credentials(accessKey, secretKey)
                                .build();
                        // 检查bucket是否存在，不存在则创建
                        boolean exists = minioClient.bucketExists(BucketExistsArgs.builder().bucket(bucketName).build());
                        if (!exists) {
                            minioClient.makeBucket(MakeBucketArgs.builder().bucket(bucketName).build());
                            log.info("创建存储桶 {}", bucketName);
                        }

                        // 上传文件
                        minioClient.putObject(
                            PutObjectArgs.builder()
                                .bucket(bucketName)
                                .object(objectName)
                                .stream(new ByteArrayInputStream(bytes), bytes.length, -1)
                                .build());

                        // 文件访问路径规则 https://BucketName.Endpoint/ObjectName

                        String fileUrl = "http://" +
//                                bucketName +
//                                "." +
                                endpoint.replace("http://", "").replace("http://", "") +
                                "/" +
                                bucketName +
                                "/" +
                                objectName;
                        log.info("文件上传到: {}", fileUrl);
                        return fileUrl;

                    } catch (Exception e) {
                        log.error("文件上传失败: {}", e.getMessage(), e);
                        throw new RuntimeException("文件上传失败", e);
                    }
                }
}
