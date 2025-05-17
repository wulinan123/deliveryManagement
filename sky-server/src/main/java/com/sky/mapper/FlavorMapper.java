package com.sky.mapper;

import com.sky.entity.DishFlavor;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @author Nan
 * @date 2025/05/17 19:53
 **/
@Mapper
public interface FlavorMapper {



    void insertBatch(List<DishFlavor> flavorsList);

}
