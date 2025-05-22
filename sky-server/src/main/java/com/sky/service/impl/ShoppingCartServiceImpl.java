package com.sky.service.impl;

import com.sky.context.BaseContext;
import com.sky.dto.ShoppingCartDTO;
import com.sky.entity.Dish;
import com.sky.entity.Setmeal;
import com.sky.entity.ShoppingCart;
import com.sky.exception.ShoppingCartBusinessException;
import com.sky.mapper.DishMapper;
import com.sky.mapper.SetMealMapper;
import com.sky.mapper.ShoppingCartMapper;
import com.sky.service.inter.ShoppingCartService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

/**
 * @author Nan
 * @date 2025/05/22 20:27
 **/
@Service
@Slf4j
public class ShoppingCartServiceImpl implements ShoppingCartService {

    @Autowired
    private ShoppingCartMapper mapper;
    @Autowired
    private DishMapper dishMapper;
    @Autowired
    private SetMealMapper setMealMapper;

    @Override
    public void add(ShoppingCartDTO shoppingCartDTO) {
        //可能情况：1.正常添加 2.已经存在 update数量
        ShoppingCart shoppingCart = new ShoppingCart();
        BeanUtils.copyProperties(shoppingCartDTO,shoppingCart);
        shoppingCart.setId(BaseContext.getCurrentId());
        List<ShoppingCart> shoppingCarts = mapper.select(shoppingCart);

        if(shoppingCarts!=null && shoppingCarts.size()==1 ){
            ShoppingCart cart = list().get(0);
            cart.setNumber(cart.getNumber()+1);
            mapper.updateNumber(cart);
        } else if (shoppingCarts==null) {
            shoppingCart.setNumber(1);
            shoppingCart.setCreateTime(LocalDateTime.now());
            if (shoppingCart.getDishId()!=null){
                // 菜品
                Dish dish = dishMapper.selectById(shoppingCart.getDishId());
                shoppingCart.setName(dish.getName());
                shoppingCart.setImage(dish.getImage());
                shoppingCart.setAmount(dish.getPrice());
            }
            else {// 套餐
                Setmeal setmeal = setMealMapper.selectById(shoppingCart.getSetmealId());
                shoppingCart.setName(setmeal.getName());
                shoppingCart.setImage(setmeal.getImage());
                shoppingCart.setAmount(setmeal.getPrice());
            }
            mapper.insert(shoppingCart);
        }
        else throw new ShoppingCartBusinessException("添加购物车错误");
    }



    @Override
    public List<ShoppingCart> list() {
        Long id = BaseContext.getCurrentId();
        ShoppingCart cart = new ShoppingCart();
        cart.setUserId(id);
        return mapper.select(cart);
    }

    @Override
    public void sub(ShoppingCartDTO shoppingCartDTO) {
        Long id = BaseContext.getCurrentId();
        ShoppingCart cart = new ShoppingCart();
        cart.setUserId(id);
        BeanUtils.copyProperties(shoppingCartDTO,cart);
        mapper.delete(cart);
    }
    @Override
    public void clean() {
        Long id = BaseContext.getCurrentId();
        ShoppingCart cart = new ShoppingCart();
        cart.setUserId(id);
        mapper.delete(cart);
    }
}
