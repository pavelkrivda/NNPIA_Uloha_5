package com.example.demo;

import com.example.demo.entity.Order;
import com.example.demo.entity.State;
import com.example.demo.repository.*;
import org.assertj.core.api.Assertions;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;
import java.util.Optional;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasSize;

@RunWith(SpringRunner.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class OrderRepositoryTest {

    private static final State TEST_STATE = State.NEW;

    @Autowired
    private OrderRepository orderRepository;

    @Test
    public void saveOrderTest() {
        Order order = new Order();
        order.setState(TEST_STATE);
        orderRepository.save(order);

        List<Order> all = orderRepository.findAll();
        assertThat(all, hasSize(1));

        Order orderFromDatabase = orderRepository.findById(order.getId()).get();
        Assertions.assertThat(orderFromDatabase.getState()).isEqualTo(TEST_STATE);
    }

    @Test
    public void deleteOrderTest() {
        Order order = new Order();
        order.setState(TEST_STATE);
        orderRepository.save(order);

        List<Order> all = orderRepository.findAll();
        assertThat(all, hasSize(1));

        orderRepository.delete(order);

        all = orderRepository.findAll();
        assertThat(all, hasSize(0));
    }

    @Test
    public void findByIdTest() {
        Order order = new Order();
        order.setState(TEST_STATE);
        orderRepository.save(order);

        Optional<Order> findOrder = orderRepository.findById(0L);
        Assert.assertNotNull(findOrder);
    }
}
