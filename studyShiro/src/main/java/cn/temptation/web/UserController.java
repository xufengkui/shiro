package cn.temptation.web;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class UserController {
    // 访问登录页
    @RequestMapping("/login")
    public String login() {
        return "login";
    }

    // 访问首页
    @RequestMapping("/index")
    public String index() {
        return "index";
    }

    // 访问公共模块
    @RequestMapping("/publicModule")
    public String publicModule() {
        return "page_public";
    }

    // 访问领导模块
    @RequestMapping("/leaderModule")
    public String leaderModule() {
        return "page_leader";
    }

    // 访问管理员模块
    @RequestMapping("/adminModule")
    public String adminModule() {
        return "page_admin";
    }

    // 登录处理
    @RequestMapping("/doLogin")
    public String doLogin(String username, String password, Model model) {
        // 使用Shiro编写认证处理
        // 1、获取Subject
        Subject subject = SecurityUtils.getSubject();

        // 2、封装用户数据
        UsernamePasswordToken token = new UsernamePasswordToken(username, password);

        // 3、执行登录
        try {
            // 登录成功
            subject.login(token);

            // 返回当前用户的帐号
            model.addAttribute("currentuser", token.getUsername());

            return "index";
        } catch (UnknownAccountException exception) {
            // 返回错误信息
            model.addAttribute("msg", "账号错误！");

            return "login";
        } catch (IncorrectCredentialsException exception) {
            // 返回错误信息
            model.addAttribute("msg", "密码错误！");

            return "login";
        }
    }

    // 注销处理
    @RequestMapping("/doLogout")
    public String doLogout() {
        // 1、获取Subject
        Subject subject = SecurityUtils.getSubject();

        // 2、执行注销
        try {
            subject.logout();
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            return "login";
        }
    }
}