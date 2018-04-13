package com.xuchen.core.shiro;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.xuchen.entity.SysUser;
import com.xuchen.service.SysRoleService;
import com.xuchen.service.SysUserService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;
import java.util.List;

public class MyShiroRealm extends AuthorizingRealm {

    @Autowired
    SysUserService sysUserService;
    @Autowired
    SysRoleService sysRoleService;

    //认证
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        //获取用户的输入的账号.
        String userName = (String) token.getPrincipal();
        EntityWrapper entityWrapper = new EntityWrapper();
        SysUser user = sysUserService.selectOne(entityWrapper.eq("user_name",userName));
        if (user == null) {
            throw new UnknownAccountException();
        }
        SimpleAuthenticationInfo authenticationInfo = new SimpleAuthenticationInfo(
                user, //用户
                user.getPassword(), //密码
                ByteSource.Util.bytes(userName),
                getName()  //realm name
        );
        //更新登录时间
        user.setLoginTime(new Date());
        sysUserService.updateById(user);
        ShiroUtils.kickOutUser(userName);
        return authenticationInfo;
    }

    //授权
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        SysUser user = (SysUser) SecurityUtils.getSubject().getPrincipal();
        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
        List<String> rolesList = sysRoleService.findRolesByUserId(user.getId());
        List<String> permsList = sysRoleService.findPermsByUserId(user.getId());
        info.addRoles(rolesList);
        info.addStringPermissions(permsList);
        return info;
    }

}
