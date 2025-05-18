package com.sky.mapper;

import com.sky.entity.SetmealDish;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @author Nan
 * @date 2025/05/18 13:50
 **/
@Mapper
public interface SetMealDishMapper {

    void insertBatch(List<SetmealDish> setmealDishes) ;
}
