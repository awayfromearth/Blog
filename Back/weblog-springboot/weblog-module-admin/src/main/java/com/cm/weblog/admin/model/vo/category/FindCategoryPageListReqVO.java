package com.cm.weblog.admin.model.vo.category;

import com.cm.weblog.common.model.BasePageQuery;
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
    private LocalDateTime startDate;

    // 创建日期截止值
    private LocalDateTime endDate;
}
