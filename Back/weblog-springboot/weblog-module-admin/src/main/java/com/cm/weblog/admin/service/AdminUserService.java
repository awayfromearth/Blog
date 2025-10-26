package com.cm.weblog.admin.service;

import com.cm.weblog.admin.model.vo.user.FindUserInfoRspVO;
import com.cm.weblog.common.utils.Response;

/**
 * 用户服务接口
 */
public interface AdminUserService {
    /**
     * 获取当前登录用户信息
     * @return 响应用户信息
     */
    Response<FindUserInfoRspVO> findUserInfo();
}
