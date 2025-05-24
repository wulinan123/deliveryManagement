package com.sky.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.sky.dto.SetmealDTO;
import com.sky.dto.SetmealPageQueryDTO;
import com.sky.entity.Setmeal;
import com.sky.entity.SetmealDish;
import com.sky.mapper.SetMealDishMapper;
import com.sky.mapper.SetMealMapper;
import com.sky.result.PageResult;
import com.sky.service.inter.SetMealService;
import com.sky.vo.DishItemVO;
import com.sky.vo.SetmealVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author Nan
 * @date 2025/05/17 21:36
 **/
@Service
@Slf4j
public class SetMealServiceImpl implements SetMealService {

    @Autowired
    SetMealMapper setMealMapper;

    @Autowired
    SetMealDishMapper setMealDishMapper;

    @Override
    public void setSetMealStatus(Long id, Integer status) {
        setMealMapper.update(
                new Setmeal().builder()
                        .id(id)
                        .status(status)
                        .build()
        );
    }

    @Override
    public void addSetMeal(SetmealDTO setmealDTO) {
        Setmeal setmeal = new Setmeal();
        BeanUtils.copyProperties(setmealDTO,setmeal);
        setMealMapper.insert(setmeal);
        Long setmealId = setmeal.getId();
        if(!setmealDTO.getSetmealDishes().isEmpty()){
            List<SetmealDish> setmealDishes = setmealDTO.getSetmealDishes();
            setmealDishes.forEach(setmealDish -> {setmealDish.setSetmealId(setmealId);});
            setMealDishMapper.insertBatch(setmealDishes);
        }
    }

    @Override
    public void updateSetMeal(SetmealDTO setmealDTO) {
        Setmeal setmeal = new Setmeal();
        BeanUtils.copyProperties(setmealDTO,setmeal);
        setMealMapper.update(setmeal);
//        List<SetmealDish> setmealDishes = setmealDTO.getSetmealDishes();
//        if(setmealDishes!=null && !setmealDishes.isEmpty()){
//            // TODO
//        }
    }

    @Override
    public Setmeal getSetMealById(Long id) {
        return setMealMapper.selectById(id);
    }

    @Override
    public PageResult getSetMealByPage(SetmealPageQueryDTO setmealPageQueryDTO) {
        PageHelper.startPage(setmealPageQueryDTO.getPage(),setmealPageQueryDTO.getPageSize());
        Page<SetmealVO> setmealPage = setMealMapper.selectByPage(setmealPageQueryDTO);
        return new PageResult(setmealPage.getTotal(),setmealPage.getResult());
    }

    @Override
    public void deleteSetMeal(Long[] ids) {
        for (Long id : ids) {
            setMealMapper.delete(id);
        }
        // TODO ： 哈需要删除另一张 表的内容
    }

    /**
     * 条件查询
     * @param setmeal
     * @return
     */
    @Override
    public List<Setmeal> list(Setmeal setmeal) {
        List<Setmeal> list = setMealMapper.list(setmeal);
        return list;
    }

    /**
     * 根据id查询菜品选项
     * @param id
     * @return
     */
    @Override
    public List<DishItemVO> getDishItemById(Long id) {
        return setMealMapper.getDishItemBySetmealId(id);
    }
}
