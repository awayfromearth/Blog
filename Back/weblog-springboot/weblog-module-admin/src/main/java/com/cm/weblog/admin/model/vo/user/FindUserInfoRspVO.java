package com.cm.weblog.admin.model.vo.user;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 获取用户信息接口返参实体类
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class FindUserInfoRspVO {
    private String username;
}
