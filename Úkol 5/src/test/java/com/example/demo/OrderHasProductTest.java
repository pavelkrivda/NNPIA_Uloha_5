package com.example.demo;

import com.example.demo.entity.Order;
import com.example.demo.entity.OrderHasProduct;
import com.example.demo.entity.Product;
import com.example.demo.entity.State;
import com.example.demo.repository.*;
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
        order.setState(State.NEW);
        orderRepository.save(order);
        Product product = new Product();
        product.setName("Auto");
        productRepository.save(product);

        hasProduct.setOrder(order);
        hasProduct.setProduct(product);
        hasProduct.setAmount(10);
        orderHasProductRepository.save(hasProduct);

        List<OrderHasProduct> all = orderHasProductRepository.findAll();
        assertThat(all, hasSize(1));
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
