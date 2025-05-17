package com.sky.service;

import com.sky.dto.DishDTO;
import com.sky.dto.DishPageQueryDTO;
import com.sky.entity.Dish;
import com.sky.result.PageResult;

public interface DishService {
    void save(DishDTO dishDTO);

    PageResult pageQuery(DishPageQueryDTO pageQueryDTO);

    void setDishStatus(Long id,Integer status);

    Dish getDishById(Long id);

    void updateDish(DishDTO dishDTO);

    void deleDish(Long[] ids);

    Dish getDishByCategoryId(Long categoryId);
}
