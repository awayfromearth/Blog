package com.cm.weblog.admin.service.impl;

import com.cm.weblog.admin.model.vo.user.FindUserInfoRspVO;
import com.cm.weblog.admin.service.AdminUserService;
import com.cm.weblog.common.utils.Response;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class AdminUserServiceImpl implements AdminUserService {
    @Override
    public Response<FindUserInfoRspVO> findUserInfo() {
        // 获取上下文中存储的信息
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        // 获取用户名
        String username = authentication.getName();

        return Response.success(FindUserInfoRspVO.builder().username(username).build());
    }
}
