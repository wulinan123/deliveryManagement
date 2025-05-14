package com.sky.mapper;

import com.github.pagehelper.Page;
import com.sky.annotation.AutoFill;
import com.sky.dto.CategoryPageQueryDTO;
import com.sky.entity.Category;
import com.sky.enumeration.OperationType;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface CategoryMapper {



     List<Category> list(Integer type);

     @AutoFill(value = OperationType.UPDATE)
     void update(Category build) ;

     Page<Category> pageQuery(CategoryPageQueryDTO categoryPageQueryDTO) ;

    @AutoFill(value = OperationType.INSERT)
    @Insert("insert into category(type, name, sort, status, create_time, update_time, create_user, update_user)" +
            " VALUES" +
            " (#{type}, #{name}, #{sort}, #{status}, #{createTime}, #{updateTime}, #{createUser}, #{updateUser})")
     void insert(Category category) ;

    @Delete("delete from category where id = #{id}")
     void delete(Long id) ;
}
