package com.sky.controller.user;

import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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


}
