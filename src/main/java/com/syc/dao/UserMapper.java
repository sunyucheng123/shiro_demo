package com.syc.dao;

import com.syc.entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface UserMapper {
    @Select("select * from user where username=#{username}")
    User selectUserByUserName(String username);

    @Select("select role_name from roles where id in(select role_id from user_role where user_id=#{id})")
    List<String> selectRolesByUserId(int id);

    @Select("select permission_name from permission where id in (select permission_id from role_permission where role_id in (select role_id from user_role where user_id=#{id}))")
    List<String> selectPermissionByUserId(int id);


}
