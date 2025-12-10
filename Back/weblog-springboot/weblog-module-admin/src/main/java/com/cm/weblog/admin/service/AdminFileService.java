package com.cm.weblog.admin.service;

import com.cm.weblog.common.utils.Response;
import org.springframework.web.multipart.MultipartFile;

public interface AdminFileService {
    /**
     * 上传文件
     * @param file 文件
     * @return 请求响应
     */
    Response<?> uploadFile(MultipartFile file);
}
