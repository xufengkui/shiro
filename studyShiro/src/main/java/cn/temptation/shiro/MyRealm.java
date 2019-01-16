package cn.temptation.shiro;

import cn.temptation.dao.ResourceDao;
import cn.temptation.dao.UserDao;
import cn.temptation.domain.User;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * 自定义Realm
 */
public class MyRealm extends AuthorizingRealm {
    @Autowired
    private UserDao userDao;
    @Autowired
    private ResourceDao resourceDao;

    // 授权处理
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        // 获取当前登录获得认证的用户
        User user = (User) principalCollection.getPrimaryPrincipal();
        // 下句语句会抛出异常交由ErrorController类根据ErrorPageConfig类中注册的响应码和错误页面处理
//        System.out.println(1 / 0);

        if (user != null) {
            // 给资源授权
            SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();

            // 根据获得认证的用户编号查询该用户具备的资源URL集合
            List<String> resourceurls = resourceDao.findByUserid(user.getUserid());

            // 遍历集合，组装成满足授权过滤器过滤格式，并添加到资源信息中
            resourceurls.forEach(item -> info.addStringPermission("user:" + item));

            return info;
        }

        return null;
    }

    // 认证处理
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        // 编写Shiro判断逻辑，判断账号和密码
        // 1、判断账号
        UsernamePasswordToken token = (UsernamePasswordToken) authenticationToken;

        User user = userDao.findByUsername(token.getUsername());
        if (user == null) {
            // 账号错误，Shiro底层会抛出UnknownAccountException异常
            return null;
        }

        // 2、判断密码
        // 只做认证，principal可以设置为空字符串
//        return new SimpleAuthenticationInfo("", user.getPassword(), "");
        // 认证后做授权处理，需要将获得认证的用户对象赋值给principal，授权处理时会用到
        return new SimpleAuthenticationInfo(user, user.getPassword(), "");
    }
}