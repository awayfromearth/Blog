package com.cm.weblog.jwt.service;

import com.cm.weblog.common.domain.dos.UserDO;
import com.cm.weblog.common.domain.dos.UserRoleDO;
import com.cm.weblog.common.domain.mapper.UserMapper;
import com.cm.weblog.common.domain.mapper.UserRoleMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * 用户详情服务实现类
 */
@Service
public class UserDetailServiceImpl implements UserDetailsService {
    @Resource
    private UserMapper userMapper;
    @Resource
    private UserRoleMapper userRoleMapper;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // 暂时先写死
        /*return User.withUsername("admin")
                .password("$2a$10$nL1x8aqM.Lfm..AYXiFVFeBBUU.Vjinc9NCSqoRrnw7E.F9s10Mxe")
                .authorities("ADMIN")
                .build();*/

        // 改为从数据库中查询
        UserDO userDO = userMapper.findByUsername(username);

        if (Objects.isNull(userDO)) {
            throw new UsernameNotFoundException("该用户不存在");
        }

        /*return User.withUsername(userDO.getUsername())
                .password(userDO.getPassword())
                .authorities("ADMIN") // 暂时先写死为 ADMIN
                .build();*/

        /*
        * 查询用户角色
        * */
        List<UserRoleDO> roleDOS = userRoleMapper.selectByUsername(username);
        String[] roleArr = new String[0];

        if (!CollectionUtils.isEmpty(roleDOS)) {
            roleArr = roleDOS.stream().map(UserRoleDO::getRole).toArray(String[]::new);
        }

        return User.withUsername(userDO.getUsername())
                .password(userDO.getPassword())
                .authorities(roleArr)
                .build();
    }
}
