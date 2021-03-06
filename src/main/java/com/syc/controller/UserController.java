package com.syc.controller;

import com.syc.entity.User;
import com.syc.util.JsonResult;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;


@Controller
@RequestMapping("/user")
public class UserController {

    @RequestMapping("/index")
    public String index(){

        return "login";
    }

    @RequestMapping("/login")
    @ResponseBody
    public JsonResult login(User user){
        Subject subject = SecurityUtils.getSubject();
        UsernamePasswordToken token=new UsernamePasswordToken(user.getUsername(),user.getPassword());
        subject.login(token);

        return new JsonResult(token);
    }

    //根据角色做权限验证
    @RequiresRoles(value = {"admin","vip"},logical = Logical.OR)
    @RequestMapping("/testRoles")
    @ResponseBody
    public String testRoles(){

        return "success admin";
    }

    //根据权限进行shiro验证
    @RequiresPermissions("sys:select")
    @RequestMapping("/testPermission")
    @ResponseBody
    public String testPermission(){

        return "success testPermission";
    }

}
