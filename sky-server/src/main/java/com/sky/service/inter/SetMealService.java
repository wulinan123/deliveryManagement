package com.sky.service.inter;

import com.sky.dto.SetmealDTO;
import com.sky.dto.SetmealPageQueryDTO;
import com.sky.entity.Setmeal;
import com.sky.result.PageResult;
import com.sky.vo.DishItemVO;

import java.util.List;

public interface SetMealService {
    void setSetMealStatus(Long id, Integer status);

    void addSetMeal(SetmealDTO setmealDTO);

    void updateSetMeal(SetmealDTO setmealDTO);

    Setmeal getSetMealById(Long id);

    PageResult getSetMealByPage(SetmealPageQueryDTO setmealPageQueryDTO);

    void deleteSetMeal(Long[] ids);


    /**
     * 条件查询
     * @param setmeal
     * @return
     */
    List<Setmeal> list(Setmeal setmeal);

    /**
     * 根据id查询菜品选项
     * @param id
     * @return
     */
    List<DishItemVO> getDishItemById(Long id);
}
