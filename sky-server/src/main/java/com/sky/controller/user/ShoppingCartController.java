package com.sky.controller.user;

import com.sky.dto.ShoppingCartDTO;
import com.sky.result.Result;
import com.sky.service.inter.ShoppingCartService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * 采用Redis缓存菜品，减少数据库操作
 * 每个分类下菜品保存一份缓存数据
 * 管理端修改菜品数据时需要清理缓存 保证数据一致性
 * @author Nan
 * @date 2025/05/18 21:01
 **/
@RestController
@RequestMapping("/user/shoppingCart")
@Api("购物车接口")
public class ShoppingCartController {

    @Autowired
    private ShoppingCartService service;
    @PostMapping("/add")
    public Result add(@RequestBody ShoppingCartDTO shoppingCartDTO){
        service.add(shoppingCartDTO);
        return Result.success("添加购物车成功");
    }
    @PostMapping("/sub")
    public Result sub(@RequestBody ShoppingCartDTO shoppingCartDTO){
        service.sub(shoppingCartDTO);
        return Result.success("修改完成");
    }

    @GetMapping("/list")
    public Result list(){
        return null;
    }

    @DeleteMapping("/clean")
    public Result clean(){
        return null;
    }
}
