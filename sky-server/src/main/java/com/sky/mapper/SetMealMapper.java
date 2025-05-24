package com.sky.mapper;

import com.github.pagehelper.Page;
import com.sky.annotation.AutoFill;
import com.sky.dto.SetmealPageQueryDTO;
import com.sky.entity.Setmeal;
import com.sky.enumeration.OperationType;
import com.sky.vo.DishItemVO;
import com.sky.vo.SetmealVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * 套餐映射器，用于处理套餐表的数据库操作
 */
@Mapper
public interface SetMealMapper {
    /**
     * 根据分类 ID 查询套餐数量
     * @param categoryId 分类 ID
     * @return 套餐数量
     */
    @Select("select count(id) from setmeal where category_id = #{categoryId}")
    Integer countByCategoryId(Long categoryId);

    /**
     * 插入新的套餐记录
     * @param setmeal 套餐实体对象
     */
    @AutoFill(value = OperationType.INSERT)
    void insert(Setmeal setmeal);

    /**
     * 根据套餐 ID 查询套餐信息
     * @param id 套餐 ID
     * @return 套餐实体对象
     */
    @Select("SELECT * FROM setmeal where id = #{id}")
    Setmeal selectById(Long id);

    /**
     * 分页查询套餐信息
     * @param setmealPageQueryDTO 套餐分页查询 DTO
     * @return 分页结果
     */
    Page<SetmealVO> selectByPage(SetmealPageQueryDTO setmealPageQueryDTO);

    /**
     * 根据套餐 ID 删除套餐记录
     * @param id 套餐 ID
     */
    void delete(Long id);

    /**
     * 更新套餐信息
     * @param setmeal 套餐实体对象
     */
    @AutoFill(value = OperationType.UPDATE)
    void update(Setmeal setmeal);

    /**
     * 动态条件查询套餐
     * @param setmeal 套餐实体对象，作为查询条件
     * @return 符合条件的套餐列表
     */
    List<Setmeal> list(Setmeal setmeal);

    /**
     * 根据套餐 ID 查询菜品选项
     * @param setmealId 套餐 ID
     * @return 菜品选项列表
     */
    @Select("SELECT sd.name, sd.copies, d.image, d.description " +
                    "FROM setmeal_dish AS sd " +
                    "LEFT JOIN dish AS d ON sd.dish_id = d.id " +
                    "WHERE sd.setmeal_id = #{setmealId}")
    List<DishItemVO> getDishItemBySetmealId(Long setmealId);
}
