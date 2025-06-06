package com.sky.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

/**
 * @author Nan
 * @date 2025/05/16 19:52
 **/

@Service
@Slf4j
public class ShopServiceImpl implements ShopService {

     @Autowired
     RedisTemplate<String ,String> redisTemplate;

     public static final String KEY = "SHOP_STATUS";


     @Override
     public String  getStatus(){
          String  status = redisTemplate.opsForValue().get(KEY);
          log.info("营业状态：{}",status);
          return status;
     }

     @Override
     public void setStatus(String status){
          log.info("营业状态设置为：{}",status);
          redisTemplate.opsForValue().set(KEY,status);
     }


}
