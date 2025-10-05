package com.cm.weblog.jwt.service;

import com.cm.weblog.common.domain.dos.UserDO;
import com.cm.weblog.common.domain.mapper.UserMapper;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Objects;

/**
 * 用户详情服务实现类
 */
@Service
public class UserDetailServiceImpl implements UserDetailsService {
    @Resource
    private UserMapper userMapper;

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

        return User.withUsername(userDO.getUsername())
                .password(userDO.getPassword())
                .authorities("ADMIN") // 暂时先写死为 ADMIN
                .build();
    }
}
