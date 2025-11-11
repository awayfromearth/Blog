package com.cm.weblog.admin.model.vo.tag;

import com.cm.weblog.common.model.BasePageQuery;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import lombok.*;

import java.time.LocalDateTime;

@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ApiModel("分页查询标签接口入参")
public class FindTagPageListReqVO extends BasePageQuery {
    // 名称
    private String name;

    // 起始日期
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss") // 注解将"yyyy-MM-dd HH:mm:ss"这种形式的时间日期字符串解析为LocalDateTime
    private LocalDateTime startDate;

    // 截止日期
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss") // 注解将"yyyy-MM-dd HH:mm:ss"这种形式的时间日期字符串解析为LocalDateTime
    private LocalDateTime endDate;
}
