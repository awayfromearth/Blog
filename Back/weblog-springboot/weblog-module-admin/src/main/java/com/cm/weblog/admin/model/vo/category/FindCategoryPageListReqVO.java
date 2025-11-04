package com.cm.weblog.admin.model.vo.category;

import com.cm.weblog.common.model.BasePageQuery;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import lombok.*;

import java.time.LocalDateTime;

/**
 * 分页查询分类入参实体类
 */
@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ApiModel(value = "分页查询分类接口数据人参实体类")
public class FindCategoryPageListReqVO extends BasePageQuery {
    // 名称
    private String name;

    // 创建日期起始值
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss") // 注解将"yyyy-MM-dd HH:mm:ss"这种形式的时间日期字符串解析为LocalDateTime
    private LocalDateTime startDate;

    // 创建日期截止值
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss") // 注解将"yyyy-MM-dd HH:mm:ss"这种形式的时间日期字符串解析为LocalDateTime
    private LocalDateTime endDate;
}
