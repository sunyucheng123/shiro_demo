package com.syc.entity;

import lombok.Data;

//用户表
@Data
public class User {
    private int id;
    private String username;//用户名字
    private String password;//密码
    private String salt;//盐值
}
