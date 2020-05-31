package com.syc.shiro.realm;

import com.syc.dao.UserMapper;
import com.syc.entity.User;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationException;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class ShiroRealm extends AuthorizingRealm {

    @Resource
    private UserMapper userMapper;

    /**
     * * 获取登录用户的权限信息并进行封装.
     * @param principalCollection
     * @return
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        //获取登录用户信息
        User user= (User) principalCollection.getPrimaryPrincipal();
        //查询数据库获取用户角色并进行校验
        List<String> roleName=userMapper.selectRolesByUserId(user.getId());
        if (roleName==null || roleName.size()==0)
            throw new AuthorizationException();
        Set<String> roles= new HashSet<>();
        for (String role: roleName) {
            roles.add(role);
        }
        //查询数据库获取用户权限
        List<String> pers=userMapper.selectPermissionByUserId(user.getId());
        if (pers==null||pers.size()==0)
            throw new AuthorizationException();
        Set<String> permissions=new HashSet<>();
        for (String per : pers) {
            permissions.add(per);
        }
        //构建授权对象
        SimpleAuthorizationInfo simpleAuthorizationInfo=new SimpleAuthorizationInfo();
        simpleAuthorizationInfo.setRoles(roles);//设置用户角色
        simpleAuthorizationInfo.setStringPermissions(permissions);//设置用户权限
        return simpleAuthorizationInfo;
    }

    /**
     * 通过此方法获取用户认证信息,并进行封装,然后返回给
     * SecurityManager对象
     * @param authenticationToken
     * @return
     * @throws AuthenticationException
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        //通过登录时提交的token获取用户名
        String username= (String) authenticationToken.getPrincipal();
        //在数据库中根据查询用户是否存在
        User user=userMapper.selectUserByUserName(username);
        //如果用户不存在，抛出异常
        if (user==null){
            throw new UnknownAccountException();
        }
        //如果用户存在，封装认证信息
        SimpleAuthenticationInfo
                simpleAuthenticationInfo=new SimpleAuthenticationInfo(user,
                                                    user.getPassword(), ByteSource.Util.bytes("mark"),getName());
        return simpleAuthenticationInfo;
    }
}
