package com.cm.weblog.admin.service.impl;

import com.cm.weblog.admin.model.vo.user.FindUserInfoRspVO;
import com.cm.weblog.admin.model.vo.user.UpdateAdminUserPasswordReqVO;
import com.cm.weblog.admin.service.AdminUserService;
import com.cm.weblog.common.domain.mapper.UserMapper;
import com.cm.weblog.common.enums.ResponseCodeEnum;
import com.cm.weblog.common.utils.Response;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class AdminUserServiceImpl implements AdminUserService {
    @Resource
    private UserMapper userMapper;

    @Resource
    private PasswordEncoder passwordEncoder;

    @Override
    public Response<FindUserInfoRspVO> findUserInfo() {
        // 获取上下文中存储的信息
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        // 获取用户名
        String username = authentication.getName();

        return Response.success(FindUserInfoRspVO.builder().username(username).build());
    }

    @Override
    public Response<?> updatePassword(UpdateAdminUserPasswordReqVO updateAdminUserPasswordReqVO) {
        // 1、拿到用户名和新密码
        String username = updateAdminUserPasswordReqVO.getUsername();
        String password = updateAdminUserPasswordReqVO.getPassword();

        // 2、加密密码
        String encodePassword = passwordEncoder.encode(password);

        // 3、更新到数据库
        int count = userMapper.updatePasswordByUsername(username, encodePassword);

        return count > 0 ? Response.success() : Response.fail(ResponseCodeEnum.USERNAME_NOT_FOUND);
    }
}
