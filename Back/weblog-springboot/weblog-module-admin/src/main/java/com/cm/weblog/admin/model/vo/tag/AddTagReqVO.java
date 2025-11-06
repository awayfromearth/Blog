package com.cm.weblog.admin.model.vo.tag;

import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import java.util.List;

/**
 * 添加标签入参实体类
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ApiModel(value = "新增标签入参实体类")
public class AddTagReqVO {
    @NotEmpty(message = "标签名称集合不能为空")
    private List<String> tags;
}
