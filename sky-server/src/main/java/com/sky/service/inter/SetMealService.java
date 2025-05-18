package com.sky.service.inter;

import com.sky.dto.SetmealDTO;
import com.sky.dto.SetmealPageQueryDTO;
import com.sky.entity.Setmeal;
import com.sky.result.PageResult;

public interface SetMealService {
    void setSetMealStatus(Long id, Integer status);

    void addSetMeal(SetmealDTO setmealDTO);

    void updateSetMeal(SetmealDTO setmealDTO);

    Setmeal getSetMealById(Long id);

    PageResult getgetSetMealByPage(SetmealPageQueryDTO setmealPageQueryDTO);

    void deleteSetMeal(Long[] ids);
}
