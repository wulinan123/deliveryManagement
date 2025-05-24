package com.sky.controller.admin;

import com.sky.dto.DishDTO;
import com.sky.dto.DishPageQueryDTO;
import com.sky.result.PageResult;
import com.sky.result.Result;
import com.sky.service.inter.DishService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CachePut;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

/**
 * @author Nan
 * @date 2025/05/17 19:11
 **/

@RestController
@RequestMapping("/admin/dish")
@Api("菜品相关接口")
@Slf4j
public class DishController {

    @Autowired
    DishService dishService;
    @Autowired
    private RedisTemplate redisTemplate;


    @PostMapping
    @ApiOperation("新增菜品")
    public Result addDish(@RequestBody DishDTO dishDTO){
        log.info("新增菜品：{}",dishDTO.getName());
        dishService.save(dishDTO);
        String  key = "dish_" + dishDTO.getCategoryId();//清除缓存
        redisTemplate.delete(key);
        return Result.success();
    }

    @PostMapping("/status/{status}")
    @ApiOperation("修改菜品状态（启用/禁用）")
    public Result setDishStatus(@PathVariable Integer status,Long id){
        log.info("修改菜品状态，ID：{}，新状态：{}", id, status);
        dishService.setDishStatus(id,status);
        String  key = "dish_" + dishService.getDishById(id).getCategoryId();//清除缓存
        redisTemplate.delete(key);
        return Result.success(dishService.getDishById(id));
    }

    @PutMapping
    @ApiOperation("修改菜品信息")
    public Result updateDish(@RequestBody DishDTO dishDTO){
        log.info("修改菜品信息，ID：{}", dishDTO.getId());
        String  key = "dish_" + dishService.getDishById(dishDTO.getId()).getCategoryId();//清除缓存
        redisTemplate.delete(key);
        dishService.updateDish(dishDTO);
        key = "dish_" + dishDTO.getCategoryId();//清除缓存
        redisTemplate.delete(key);
        return Result.success(dishService.getDishById(dishDTO.getId()));
    }

    @DeleteMapping
    @ApiOperation("批量删除菜品")
    public Result deleteDish(Long[] ids ){
        log.info("批量删除菜品，ID列表：{}", java.util.Arrays.toString(ids));
        dishService.deleDish(ids);

        Set<Object> keys = redisTemplate.keys("dish_*");
        redisTemplate.delete(keys);

        return Result.success("删除成功");
    }

    @GetMapping("/{id}")
    @ApiOperation("根据ID查询菜品详情")
    public Result getDishById(@PathVariable Long id){
        log.info("查询菜品详情，ID：{}", id);
        return Result.success(dishService.getDishById(id));
    }

    @GetMapping("/list")
    @ApiOperation("根据分类ID查询菜品列表")
    public Result getDishByList(Long categoryId){
        log.info("根据分类ID查询菜品列表，分类ID：{}", categoryId);
        //TODO：这里是错误的！应该返回 List
        return Result.success(dishService.getDishByCategoryId(categoryId));
    }
    @GetMapping("/page")
    @ApiOperation("分页查询菜品信息")
    public Result<PageResult> getDishByPage(DishPageQueryDTO pageQueryDTO){
        log.info("分页查询菜品，参数：{}", pageQueryDTO);
        PageResult pageResult = dishService.pageQuery(pageQueryDTO);
        return Result.success(pageResult);
    }

}
