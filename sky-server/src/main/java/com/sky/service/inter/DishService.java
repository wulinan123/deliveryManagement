package com.sky.service.inter;

import com.sky.dto.DishDTO;
import com.sky.dto.DishPageQueryDTO;
import com.sky.entity.Dish;
import com.sky.result.PageResult;
import com.sky.vo.DishVO;

import java.util.List;

public interface DishService {
    void save(DishDTO dishDTO);

    PageResult pageQuery(DishPageQueryDTO pageQueryDTO);

    void setDishStatus(Long id,Integer status);

    Dish getDishById(Long id);

    void updateDish(DishDTO dishDTO);

    void deleDish(Long[] ids);

    Dish getDishByCategoryId(Long categoryId);

    List<Dish> list(Long categoryId);

    /**
     * 条件查询菜品和口味
     * @param dish
     * @return
     */
    List<DishVO> listWithFlavor(Dish dish);
}
