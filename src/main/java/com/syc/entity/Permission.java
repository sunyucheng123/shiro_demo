package com.syc.entity;

import lombok.Data;

//权限表数据
@Data
public class Permission {
    Integer id;//权限id
    String permissionName;//权限名称
    String description;//描述
}
