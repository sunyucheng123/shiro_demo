package com.syc.entity;

import lombok.Data;

//角色-权限表
@Data
public class RolePermision {
    Integer id;//
    Integer rolesId;//角色id
    Integer permissionId;//权限id
}
