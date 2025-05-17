package com.sky.controller.admin;

import com.sky.constant.StatusConstant;
import com.sky.dto.DishDTO;
import com.sky.dto.DishPageQueryDTO;
import com.sky.result.PageResult;
import com.sky.result.Result;
import com.sky.service.DishService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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


    @PostMapping
    @ApiOperation("新增菜品")
    public Result addDish(@RequestBody DishDTO dishDTO){
        log.info("新增菜品：{}",dishDTO.getName());
        dishService.save(dishDTO);
        return Result.success();
    }

    @PostMapping("/status/{status}")
    public Result setDishStatus(@PathVariable Integer status,Long id){
        log.info("修改菜品状态为：{}", status);
        dishService.setDishStatus(id,status);
        return Result.success(dishService.getDishById(id));
    }

    @PutMapping
    public Result updateDish(@RequestBody DishDTO dishDTO){
        dishService.updateDish(dishDTO);
        return Result.success(dishService.getDishById(dishDTO.getId()));

    }

    @DeleteMapping
    public Result deleteDish(Long[] ids ){
        dishService.deleDish(ids);
        return Result.success("删除成功");
    }

    @GetMapping("/{id}")
    public Result getDishById(@PathVariable Long id){
        return Result.success(dishService.getDishById(id));
    }

    @GetMapping("/list")
    public Result getDishByList(Long categoryId){
        //TODO：这里是错误的！应该返回 List
        return Result.success(dishService.getDishByCategoryId(categoryId));
    }
    @GetMapping("/page")
    public Result<PageResult> getDishByPage(DishPageQueryDTO pageQueryDTO){
        log.info("分页查询：");
        PageResult pageResult = dishService.pageQuery(pageQueryDTO);
        return Result.success(pageResult);
    }






}
