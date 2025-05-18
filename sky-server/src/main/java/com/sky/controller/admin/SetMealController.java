package com.sky.controller.admin;

import com.sky.dto.SetmealDTO;
import com.sky.dto.SetmealPageQueryDTO;
import com.sky.result.Result;
import com.sky.service.inter.SetMealService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author Nan
 * @date 2025/05/17 21:35
 **/
@RestController
@RequestMapping("/admin/setmeal")
@Api("套餐相关接口")
public class SetMealController {

    @Autowired
    SetMealService service;

    @PostMapping
    @ApiOperation("新增套餐")
    public Result addSetMeal(@RequestBody SetmealDTO setmealDTO){
        service.addSetMeal(setmealDTO);
        return Result.success("新增套餐");
    }

    @PostMapping("/status/{status}")
    @ApiOperation("套餐停售、起售")
    public Result setSetMealStatus(@PathVariable Integer status,Long id){
        service.setSetMealStatus(id,status);
        return Result. success("修改套餐状态完成");
    }

    @PutMapping
    @ApiOperation("修改套餐")
    public Result updateSetMeal(@RequestBody SetmealDTO setmealDTO){
        service.updateSetMeal(setmealDTO);
        return Result. success("更新套餐完成");
    }

    @GetMapping("/{id}")
    @ApiOperation("根据 id 查询套餐")
    public Result getSetMealById(@PathVariable Long id){
        return Result.success(service.getSetMealById(id));
    }

    @GetMapping("/page")
    @ApiOperation("分页查询")
    public Result getSetMealByPage(@RequestBody SetmealPageQueryDTO setmealPageQueryDTO){
        return Result.success(
                service.getgetSetMealByPage(setmealPageQueryDTO)
        );
    }

    @DeleteMapping
    @ApiOperation("批量删除套餐")
    public Result deleteSetMeal(Long[] ids){
        service.deleteSetMeal(ids);
        return Result.success("删除套餐完成");
    }

}
