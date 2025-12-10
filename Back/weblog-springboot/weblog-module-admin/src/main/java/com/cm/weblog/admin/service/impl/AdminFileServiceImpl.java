package com.cm.weblog.admin.service.impl;

import com.cm.weblog.admin.model.vo.file.UploadFileRspVO;
import com.cm.weblog.admin.service.AdminFileService;
import com.cm.weblog.admin.utils.MinioUtil;
import com.cm.weblog.common.enums.ResponseCodeEnum;
import com.cm.weblog.common.exception.BizException;
import com.cm.weblog.common.utils.Response;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;

@Service
@Slf4j
public class AdminFileServiceImpl implements AdminFileService {
    @Resource
    private MinioUtil minioUtil;

    @Override
    public Response<?> uploadFile(MultipartFile file) {
        try {
            String url = minioUtil.uploadFile(file);

            return Response.success(UploadFileRspVO.builder().url(url).build());
        } catch (Exception e) {
            log.error("==> 文件上传出错：", e);
            throw new BizException(ResponseCodeEnum.FILE_UPLOAD_FAILED);
        }
    }
}
