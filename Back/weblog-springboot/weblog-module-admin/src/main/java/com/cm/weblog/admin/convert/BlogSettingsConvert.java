package com.cm.weblog.admin.convert;

import com.cm.weblog.admin.model.vo.blogSettings.FindBlogSettingsRspVO;
import com.cm.weblog.admin.model.vo.blogSettings.UpdateBlogSettingsReqVO;
import com.cm.weblog.common.domain.dos.BlogSettingsDO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface BlogSettingsConvert {
    // 初始化转换器实例
    BlogSettingsConvert INSTANCE = Mappers.getMapper(BlogSettingsConvert.class);

    /**
     * 博客设置：DO 转 VO
     * @param bean DO
     * @return VO
     */
    FindBlogSettingsRspVO convertDO2VO(BlogSettingsDO bean);
}
