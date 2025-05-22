package com.sky.mapper;

import com.sky.dto.ShoppingCartDTO;
import com.sky.entity.ShoppingCart;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Update;

import java.util.List;

/**
 * @author Nan
 * @date 2025/05/22 20:32
 **/
@Mapper
public interface ShoppingCartMapper {
    List<ShoppingCart> select(ShoppingCart shoppingCart);

    @Update("update shopping_cart set number = #{number} where id=#{id}")
    void updateNumber(ShoppingCart cart);

    void insert(ShoppingCart shoppingCart);

    void delete(ShoppingCart cart);
}
