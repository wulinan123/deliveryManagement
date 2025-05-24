package com.sky.mapper;

import com.sky.entity.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

/**
 * @author Nan
 * @date 2025/05/18 16:08
 * @description 用户映射器，用于处理用户表的数据库操作
 **/
@Mapper
public interface UserMapper {

    /**
     * 根据 openid 查询用户信息
     * @param openid 用户的 openid
     * @return 用户实体对象
     */
    @Select("select * from user where openid = #{openid}")
    User selectByOpenId(String openid);

    /**
     * 插入新用户信息
     * @param user 用户实体对象
     */
    void insert(User user);
}
