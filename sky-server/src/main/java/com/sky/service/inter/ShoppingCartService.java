package com.sky.service.inter;

import com.sky.dto.ShoppingCartDTO;
import com.sky.entity.ShoppingCart;
import com.sky.result.Result;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

public interface ShoppingCartService {
    public void add( ShoppingCartDTO shoppingCartDTO);

    public void sub( ShoppingCartDTO shoppingCartDTO);

    public List<ShoppingCart> list();

    public void clean();
}
