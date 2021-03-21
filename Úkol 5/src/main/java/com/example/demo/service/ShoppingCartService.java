package com.example.demo.service;

import com.example.demo.entity.Product;

import java.util.Map;

public interface ShoppingCartService {

    void add(Long id);

    void remove(Long id);

    Map<Product, Integer> getCart();

    void checkout();

    void storno();
}
