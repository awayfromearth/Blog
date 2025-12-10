package com.cm.weblog.admin.utils;

import com.cm.weblog.admin.config.MinioProperties;
import io.minio.MinioClient;
import io.minio.PutObjectArgs;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.util.UUID;

@Component
@Slf4j
public class MinioUtil {
    @Resource
    private MinioProperties minioProperties;

    @Resource
    private MinioClient minioClient;

    public String uploadFile(MultipartFile file) throws Exception {
        // 1、判断文件是否为空
        if (file == null || file.getSize() <= 0) {
            log.error("==> 上传文件异常：文件为空");
            throw new RuntimeException("文件不能为空");
        }

        /*
        * 2、获取文件相关信息
        * */
        // 原始名称
        String fileName = file.getOriginalFilename();
        // 类型
        String contentType = file.getContentType();

        /*
        * 3、生成存储信息
        * */
        // 名称
        String key = UUID.randomUUID().toString().replace("-", "");
        // 后缀
        assert fileName != null;
        String suffix = fileName.substring(fileName.lastIndexOf("."));
        // 拼接
        String objectName = String.format("%s%s", key, suffix);

        log.info("==> 文件开始上传至 Minio ，ObjectName: {}", objectName);

        /*
        * 4、上传文件
        * */
        minioClient.putObject(PutObjectArgs.builder()
                .bucket(minioProperties.getBucketName())
                .object(objectName)
                .stream(file.getInputStream(), file.getSize(), -1)
                .contentType(contentType)
                .build());

        /*
        * 5、返回链接
        * */
        String url = String.format("%s/%s/%s", minioProperties.getEndpoint(), minioProperties.getBucketName(), objectName);
        log.info("==> 文件上传成功，访问路径：{}", url);

        return url;
    }
}
