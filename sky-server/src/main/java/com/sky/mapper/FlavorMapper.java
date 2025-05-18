package com.sky.mapper;

import com.sky.entity.DishFlavor;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * @author Nan
 * @date 2025/05/17 19:53
 **/
/**
 * @author Nan
 * @date 2025/05/17 19:53
 * @description 菜品口味映射器，用于处理菜品口味表的数据库操作
 **/
@Mapper
public interface FlavorMapper {

    /**
     * 批量插入菜品口味信息
     * @param flavorsList 菜品口味信息列表
     */
    void insertBatch(List<DishFlavor> flavorsList);



    /**
     * 根据菜品 ID 查询菜品口味信息
     * @param id 菜品 ID
     * @return 菜品口味信息列表
     */
    List<DishFlavor> getByDishId(Long id);
}
