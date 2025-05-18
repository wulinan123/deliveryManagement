package com.sky.controller.user;

import com.sky.result.Result;
import com.sky.service.impl.ShopService;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author Nan
 * @date 2025/05/16 19:51
 * 对于这种简单数据，单独建表有点浪费，因此放在Redis
 **/
@RestController("userShopController")
@RequestMapping("/user/shop")
@Slf4j
@Api("店铺操作接口")
public class ShopController {

    @Autowired
    ShopService shopService;

    @GetMapping("/status")
    public Result getStatus(){
        return Result.success(Integer.valueOf(shopService.getStatus()));
    }


}
