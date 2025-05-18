package com.sky.mapper;

import com.github.pagehelper.Page;
import com.sky.annotation.AutoFill;
import com.sky.dto.DishPageQueryDTO;
import com.sky.entity.Dish;
import com.sky.enumeration.OperationType;
import com.sky.vo.DishVO;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * @author Nan
 * @description 菜品映射器，用于处理菜品表的数据库操作
 **/
@Mapper
public interface DishMapper {
    /**
     * 示例方法注释，根据实际方法功能修改
     * @param param 示例参数，根据实际参数修改
     * @return 示例返回值，根据实际返回值修改
     */
    /**
     * 根据分类id查询菜品数量
     * @param categoryId
     * @return
     */
    @Select("select count(id) from dish where category_id = #{categoryId}")
    Integer countByCategoryId(Long categoryId);


    /**
     * 插入新的菜品记录
     * @param dish 菜品实体对象
     */
    @AutoFill(value = OperationType.INSERT)
    void insert(Dish dish);


    /**
     * 分页查询菜品信息
     * @param pageQueryDTO 菜品分页查询 DTO
     * @return 分页结果
     */
    Page<DishVO> pageQuery(DishPageQueryDTO pageQueryDTO);

    /**
     * 更新菜品信息
     * @param dish 菜品实体对象
     */
    @AutoFill(value = OperationType.UPDATE)
    void update(Dish dish);


    /**
     * 根据菜品 ID 查询菜品信息
     * @param id 菜品 ID
     * @return 菜品实体对象
     */
    @Select("select * from dish where id = #{id}")
    Dish selectById(Long id);

    /**
     * 根据菜品 ID 删除菜品记录
     * @param id 菜品 ID
     */
    @Delete("DELETE from dish where id = #{id}")
    void deleteById(Long id);


    /**
     * 根据分类 ID 查询菜品信息
     * @param categoryId 分类 ID
     * @return 菜品实体对象
     */
    @Select("select * from dish where category_id = #{categoryId}")
    Dish selectByCategoryId(Long categoryId);

    /**
     * 动态条件查询菜品
     * @param dish 菜品实体对象，作为查询条件
     * @return 符合条件的菜品列表
     */
    List<Dish> list(Dish dish);
}
