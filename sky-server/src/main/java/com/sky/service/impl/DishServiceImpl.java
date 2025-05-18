package com.sky.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.sky.dto.DishDTO;
import com.sky.dto.DishPageQueryDTO;
import com.sky.entity.Dish;
import com.sky.entity.DishFlavor;
import com.sky.mapper.DishMapper;
import com.sky.mapper.FlavorMapper;
import com.sky.result.PageResult;
import com.sky.service.inter.DishService;
import com.sky.vo.DishVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author Nan
 * @date 2025/05/17 19:16
 **/
@Service
@Slf4j
public class DishServiceImpl implements DishService {

    @Autowired
    private DishMapper dishMapper;

    @Autowired
    private FlavorMapper flavorMapper;

    @Override
    @Transactional //保证数据一致性，使得数据库操作要么成功要么全部回滚
    public void save(DishDTO dishDTO) {
        Dish dish = new Dish();
        BeanUtils.copyProperties(dishDTO,dish); //口味是 List，不会被拷贝进去
        dishMapper.insert(dish);
        Long dishId = dish.getId();
        log.info("DishId：{} ",dishId);

        List<DishFlavor> flavorsList = dishDTO.getFlavors();

        if(flavorsList!=null && !flavorsList.isEmpty()) {
            flavorsList.forEach(tmp->{tmp.setDishId(dishId);});
            flavorMapper.insertBatch(flavorsList);
        }




    }

    @Override
    public PageResult pageQuery(DishPageQueryDTO pageQueryDTO) {
        PageHelper.startPage(pageQueryDTO.getPage(),pageQueryDTO.getPageSize());
        Page<DishVO> page = dishMapper.pageQuery(pageQueryDTO);
        return new PageResult(page.getTotal(),page.getResult());
    }

    @Override
    public void setDishStatus(Long id, Integer status) {
        Dish dish = Dish.builder().id(id).status(status).build();
        dishMapper.update(dish);
    }

    @Override
    public Dish getDishById(Long id) {
        return dishMapper.selectById(id);
    }

    @Override
    public void updateDish(DishDTO dishDTO) {
        Dish dish = new Dish();
        BeanUtils.copyProperties(dishDTO,dish);
        dishMapper.update(dish);
    }

    @Override
    public void deleDish(Long[] ids) {
        for (Long id : ids){
            dishMapper.deleteById(id);
        }
    }

    @Override
    public Dish getDishByCategoryId(Long categoryId) {
        return dishMapper.selectByCategoryId(categoryId);
    }
}
