package com.example.demo.service;

import com.example.demo.entity.Order;
import com.example.demo.entity.OrderHasProduct;
import com.example.demo.entity.Product;
import com.example.demo.entity.State;
import com.example.demo.repository.OrderHasProductRepository;
import com.example.demo.repository.OrderRepository;
import com.example.demo.repository.ProductRepository;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.NoSuchElementException;

@Service
public class ShoppingCartServiceImpl implements ShoppingCartService {

    private final ProductRepository productRepository;
    private final OrderRepository orderRepository;
    private final OrderHasProductRepository orderHasProductRepository;
    private final Map<Product, Integer> cart;

    public ShoppingCartServiceImpl(ProductRepository productRepository, OrderRepository orderRepository,
                                   OrderHasProductRepository orderHasProductRepository) {

        this.productRepository = productRepository;
        this.orderRepository = orderRepository;
        this.orderHasProductRepository = orderHasProductRepository;
        cart = new HashMap<>();
    }

    @Override
    public void add(Long id) {
        Product product = productRepository.findById(id).orElseThrow(NoSuchElementException::new);

        if (cart.containsKey(product)) {
            cart.replace(product, cart.get(product) + 1);
        } else {
            cart.put(product, 1);
        }
    }

    @Override
    public void remove(Long id) {
        Product product = productRepository.findById(id).orElseThrow(NoSuchElementException::new);

        if (cart.containsKey(product)) {
            if (cart.get(product) > 1) {
                cart.replace(product, cart.get(product) - 1);
            } else {
                cart.remove(product);
            }
        }
    }

    @Override
    public Map<Product, Integer> getCart() {
        return cart;
    }

    @Override
    public void checkout() {
        Order order = new Order();
        order.setState(State.NEW);
        orderRepository.save(order);

        for (Map.Entry<Product, Integer> entry : cart.entrySet()) {
            OrderHasProduct orderHasProduct = new OrderHasProduct();
            orderHasProduct.setOrder(order);
            orderHasProduct.setProduct(entry.getKey());
            orderHasProduct.setAmount(entry.getValue());
            orderHasProductRepository.save(orderHasProduct);
        }

        cart.clear();
    }

    @Override
    public void storno() {
        cart.clear();
    }
}
