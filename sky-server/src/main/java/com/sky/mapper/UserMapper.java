package com.sky.mapper;

import com.sky.entity.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

/**
 * @author Nan
 * @date 2025/05/18 16:08
 **/
@Mapper
public interface UserMapper {


    @Select("select * from user where openid = #{openid}")
    User selectByOpenId(String openid);

    void insert(User user);
}
