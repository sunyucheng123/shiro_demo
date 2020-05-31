package com.syc.entity;

import lombok.Data;

//用户-角色表

@Data
public class UserRoles {
    Integer id;
    Integer userId;//用户id
    Integer roles;//角色id
}
