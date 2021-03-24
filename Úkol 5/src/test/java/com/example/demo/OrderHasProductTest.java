package com.example.demo;

import com.example.demo.entity.*;
import com.example.demo.repository.*;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasSize;

@RunWith(SpringRunner.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class OrderHasProductTest {

    private static final State TEST_ORDER_STATE = State.NEW;
    private static final String TEST_PRODUCT_NAME = "Auto";
    private static final String TEST_DESCRIPTION_NAME = "Popis";
    private static final int TEST_AMOUNT = 10;

    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private OrderHasProductRepository orderHasProductRepository;
    @Autowired
    private OrderRepository orderRepository;

    @Test
    public void saveOrderHasProductTest() {
        OrderHasProduct hasProduct = new OrderHasProduct();

        Order order = new Order();
        order.setState(TEST_ORDER_STATE);
        orderRepository.save(order);
        Product product = new Product();
        product.setName(TEST_PRODUCT_NAME);
        product.setDescription(TEST_DESCRIPTION_NAME);
        product.setName(TEST_PRODUCT_NAME);
        productRepository.save(product);

        hasProduct.setOrder(order);
        hasProduct.setProduct(product);
        hasProduct.setAmount(TEST_AMOUNT);
        orderHasProductRepository.save(hasProduct);

        List<OrderHasProduct> all = orderHasProductRepository.findAll();
        assertThat(all, hasSize(1));

        OrderHasProduct orderHasProductFromDatabase = orderHasProductRepository.findById(hasProduct.getId()).get();
        Assertions.assertThat(orderHasProductFromDatabase.getAmount()).isEqualTo(TEST_AMOUNT);
        Assertions.assertThat(orderHasProductFromDatabase.getProduct().getName()).isEqualTo(TEST_PRODUCT_NAME);
        Assertions.assertThat(orderHasProductFromDatabase.getProduct().getDescription()).isEqualTo(TEST_DESCRIPTION_NAME);
        Assertions.assertThat(orderHasProductFromDatabase.getOrder().getState()).isEqualTo(TEST_ORDER_STATE);
    }

    @Test
    public void deleteOrderHasProductTest() {
        OrderHasProduct hasProduct = new OrderHasProduct();
        orderHasProductRepository.save(hasProduct);

        List<OrderHasProduct> all = orderHasProductRepository.findAll();
        assertThat(all, hasSize(1));

        orderHasProductRepository.delete(hasProduct);

        all = orderHasProductRepository.findAll();
        assertThat(all, hasSize(0));
    }
}
