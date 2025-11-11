package com.cm.weblog.admin.model.vo.tag;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * 分页查询标签接口响应实体类
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class FindTagPageListRspVO {
    // 标签 ID
    private Long id;

    // 标签名称
    private String name;

    // 标签创建时间
    private LocalDateTime createTime;
}
