package com.sky.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.sky.constant.StatusConstant;
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

import java.util.ArrayList;
import java.util.List;

/**
 * 菜品服务实现类，提供菜品的增删改查、状态管理及关联口味查询等功能
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

    /**
     * 保存菜品信息及关联的口味数据
     * @param dishDTO 菜品数据传输对象，包含菜品基本信息及口味列表
     */
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

    /**
     * 分页查询菜品信息
     * @param pageQueryDTO 分页查询条件（页码、每页数量、查询关键字等）
     * @return 分页结果对象，包含总记录数和当前页菜品列表
     */
    @Override
    public PageResult pageQuery(DishPageQueryDTO pageQueryDTO) {
        PageHelper.startPage(pageQueryDTO.getPage(),pageQueryDTO.getPageSize());
        Page<DishVO> page = dishMapper.pageQuery(pageQueryDTO);
        return new PageResult(page.getTotal(),page.getResult());
    }

    /**
     * 设置菜品的售卖状态（启用/禁用）
     * @param id 菜品ID
     * @param status 状态值（1-启用，0-禁用）
     */
    @Override
    public void setDishStatus(Long id, Integer status) {
        Dish dish = Dish.builder().id(id).status(status).build();
        dishMapper.update(dish);
    }

    /**
     * 根据ID查询具体菜品信息
     * @param id 菜品ID
     * @return 对应的菜品实体对象
     */
    @Override
    public Dish getDishById(Long id) {
        return dishMapper.selectById(id);
    }

    /**
     * 更新菜品基本信息（不包含口味）
     * @param dishDTO 包含更新后菜品信息的数据传输对象
     */
    @Override
    public void updateDish(DishDTO dishDTO) {
        Dish dish = new Dish();
        BeanUtils.copyProperties(dishDTO,dish);
        dishMapper.update(dish);
    }

    /**
     * 批量删除菜品（根据ID数组）
     * @param ids 需要删除的菜品ID数组
     */
    @Override
    public void deleDish(Long[] ids) {
        for (Long id : ids){
            dishMapper.deleteById(id);
        }
    }

    /**
     * 根据分类ID查询关联的菜品（单条）
     * @param categoryId 分类ID
     * @return 该分类下的菜品实体对象（若存在）
     */
    @Override
    public Dish getDishByCategoryId(Long categoryId) {
        return dishMapper.selectByCategoryId(categoryId);
    }

    /**
     * 根据分类ID查询当前启用的菜品列表
     * @param categoryId 分类ID
     * @return 启用状态的菜品列表
     */
    @Override
    public List<Dish> list(Long categoryId) {
        Dish dish = Dish.builder()
                .categoryId(categoryId)
                .status(StatusConstant.ENABLE)
                .build();
        return dishMapper.list(dish);
    }


    /**
     * 条件查询菜品并关联其口味信息
     * @param dish 查询条件（如分类ID、状态等）
     * @return 包含口味信息的菜品VO列表
     */
    public List<DishVO> listWithFlavor(Dish dish) {
        List<Dish> dishList = dishMapper.list(dish);
        List<DishVO> dishVOList = new ArrayList<>();

        for (Dish d : dishList) {
            DishVO dishVO = new DishVO();
            BeanUtils.copyProperties(d,dishVO);

            //根据菜品id查询对应的口味
            List<DishFlavor> flavors = flavorMapper.getByDishId(d.getId());

            dishVO.setFlavors(flavors);
            dishVOList.add(dishVO);
        }

        return dishVOList;
    }
}
