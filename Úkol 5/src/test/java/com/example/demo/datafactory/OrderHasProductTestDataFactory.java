package com.example.demo.datafactory;

import com.example.demo.entity.*;
import com.example.demo.repository.OrderHasProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class OrderHasProductTestDataFactory {

    public static final int TEST_AMOUNT = 10;

    @Autowired
    private OrderHasProductRepository orderHasProductRepository;

    @Autowired
    private OrderTestDataFactory orderTestDataFactory;

    @Autowired
    private ProductTestDataFactory productTestDataFactory;

    public void saveOrderHasProduct() {
        OrderHasProduct orderHasProduct = new OrderHasProduct();
        Order order =  orderTestDataFactory.getAndSaveOrder();
        Product product = productTestDataFactory.getAndSaveOrder();

        orderHasProduct.setOrder(order);
        orderHasProduct.setProduct(product);
        orderHasProduct.setAmount(TEST_AMOUNT);
        orderHasProductRepository.save(orderHasProduct);
    }

    public void saveOrderHasProduct(OrderHasProduct orderHasProduct) {
        if(orderHasProduct.getAmount() == null) orderHasProduct.setAmount(TEST_AMOUNT);
        if(orderHasProduct.getOrder() == null) orderHasProduct.setOrder(orderTestDataFactory.getAndSaveOrder());
        if(orderHasProduct.getProduct() == null) orderHasProduct.setProduct(productTestDataFactory.getAndSaveOrder());
        orderHasProductRepository.save(orderHasProduct);
    }
}
