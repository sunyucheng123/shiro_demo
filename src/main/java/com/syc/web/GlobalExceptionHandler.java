package com.syc.web;

import com.syc.util.JsonResult;
import org.apache.shiro.ShiroException;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authz.AuthorizationException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(RuntimeException.class)
    public JsonResult doHandleRuntimeException(RuntimeException e){
        e.printStackTrace();
        return new JsonResult(e.getMessage());
    }

    @ExceptionHandler(ShiroException.class)
    @ResponseBody
    public JsonResult doHandleShiroException(ShiroException e){
        JsonResult result = new JsonResult();
        result.setState(0);
        if(e instanceof UnknownAccountException) {
            result.setMessage("账户不存在");
        }else if(e instanceof LockedAccountException) {
            result.setMessage("账户已被禁用");
        }else if(e instanceof IncorrectCredentialsException) {
            result.setMessage("密码不正确");
        }else if(e instanceof AuthorizationException) {
            result.setMessage("没有此操作权限");
        }else {
            result.setMessage("系统维护中");
        }
        e.printStackTrace();
        return result;
    }
}
