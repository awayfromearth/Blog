package com.cm.weblog.admin.model.vo.category;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * 分页查询分类响应实体类
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class FindCategoryPageListRspVO {
    // ID
    private Long id;

    // 名称
    private String name;

    // 创建时间
    private LocalDateTime createTime;
}
