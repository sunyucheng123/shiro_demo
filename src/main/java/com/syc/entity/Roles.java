package com.syc.entity;

import lombok.Data;

//角色表
@Data
public class Roles {
    Integer id;
    String roleName;//角色名称
    String description;//描述
}
