package com.sky.mapper;

import com.github.pagehelper.Page;
import com.sky.annotation.AutoFill;
import com.sky.dto.CategoryPageQueryDTO;
import com.sky.entity.Category;
import com.sky.enumeration.OperationType;
import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 * @author Nan
 * @description 分类映射器，用于处理分类表的数据库操作
 **/
@Mapper
public interface CategoryMapper {

    /**
     * 根据类型查询分类列表
     * @param type 分类类型
     * @return 分类列表
     */
    List<Category> list(Integer type);

    /**
     * 更新分类信息
     * @param build 分类实体对象
     */
    @AutoFill(value = OperationType.UPDATE)
    void update(Category build) ;

    /**
     * 分页查询分类信息
     * @param categoryPageQueryDTO 分页查询参数
     * @return 分页结果
     */
    Page<Category> pageQuery(CategoryPageQueryDTO categoryPageQueryDTO) ;

    /**
     * 插入新的分类信息
     * @param category 分类实体对象
     */
    @AutoFill(value = OperationType.INSERT)
    @Insert("insert into category(type, name, sort, status, create_time, update_time, create_user, update_user)" +
            " VALUES" +
            " (#{type}, #{name}, #{sort}, #{status}, #{createTime}, #{updateTime}, #{createUser}, #{updateUser})")
    void insert(Category category) ;

    /**
     * 根据 ID 删除分类信息
     * @param id 分类 ID
     */
    @Delete("delete from category where id = #{id}")
    void delete(Long id) ;
}
