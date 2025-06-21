package com.service;

import com.dto.ShoppingCartDTO;
import com.entity.ShoppingCart;

import java.util.List;

public interface ShoppingCartService {
    void saveShoppingCart(ShoppingCartDTO shoppingCartDTO);

    List<ShoppingCart> findAll();

    void deleteAll();

    void subShoppingCart(ShoppingCartDTO shoppingCartDTO);
}
