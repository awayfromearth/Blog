package com.cm.weblog.admin.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cm.weblog.admin.convert.BlogSettingsConvert;
import com.cm.weblog.admin.model.vo.blogSettings.FindBlogSettingsRspVO;
import com.cm.weblog.admin.service.AdminBlogSettingsService;
import com.cm.weblog.common.domain.dos.BlogSettingsDO;
import com.cm.weblog.common.domain.mapper.BlogSettingsMapper;
import com.cm.weblog.common.utils.Response;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class AdminBlogSettingsServiceImpl extends ServiceImpl<BlogSettingsMapper, BlogSettingsDO> implements AdminBlogSettingsService {
    @Resource
    private BlogSettingsMapper blogSettingsMapper;

    @Override
    public Response<?> findBlogSettingDetail() {
        // 1、查询 ID 为 1 的数据
        BlogSettingsDO blogSettingsDO = blogSettingsMapper.selectById(1L);

        // 转化为 VO
        FindBlogSettingsRspVO vo = BlogSettingsConvert.INSTANCE.convertDO2VO(blogSettingsDO);

        return Response.success(vo);
    }
}
