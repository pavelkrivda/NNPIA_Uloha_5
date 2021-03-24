package com.example.demo;

import com.example.demo.datafactory.Creator
import com.example.demo.entity.Order
import com.example.demo.entity.State
import com.example.demo.repository.OrderRepository
import org.assertj.core.api.Assertions;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit4.SpringRunner;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasSize;

@RunWith(SpringRunner.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Import([Creator.class])
public class OrderRepositoryGroovyTest {

    public static final State TEST_STATE = State.NEW;
    public static final String TEST_FIRST_NAME = "Test firstName";
    public static final String TEST_LAST_NAME = "Test lastName";
    public static final int TEST_AGE = 0;
    public static final String TEST_CITY = "Test city";
    public static final String TEST_ADDRESS_STATE = "Test state";
    public static final int TEST_POSLAT_CODE = 0;

    @Autowired
    private Creator creator;

    @Autowired
    private OrderRepository orderRepository;

    @Test
    public void saveOrderTest() {
        Order order = new Order(state: TEST_STATE);
        creator.save(order);

        List<Order> all = orderRepository.findAll();
        assertThat(all, hasSize(1));

        Order readFromDatabase = orderRepository.findById(order.getId()).get();
        Assertions.assertThat(readFromDatabase.getState()).isEqualTo(TEST_STATE);
        Assertions.assertThat(readFromDatabase.getPerson().firstName).isEqualTo(TEST_FIRST_NAME);
        Assertions.assertThat(readFromDatabase.getPerson().lastName).isEqualTo(TEST_LAST_NAME);
        Assertions.assertThat(readFromDatabase.getPerson().age).isEqualTo(TEST_AGE);
        Assertions.assertThat(readFromDatabase.getPerson().address.city).isEqualTo(TEST_CITY);
        Assertions.assertThat(readFromDatabase.getPerson().address.state).isEqualTo(TEST_ADDRESS_STATE);
        Assertions.assertThat(readFromDatabase.getPerson().address.postalCode).isEqualTo(TEST_POSLAT_CODE);
    }

    @Test
    public void deleteOrderTest() {
        Order order = new Order(state: TEST_STATE);
        creator.save(order);

        List<Order> all = orderRepository.findAll();
        assertThat(all, hasSize(1));

        orderRepository.delete(order);

        all = orderRepository.findAll();
        assertThat(all, hasSize(0));
    }

    @Test
    public void findByIdTest() {
        Order order = new Order(state: TEST_STATE);
        creator.save(order);

        Optional<Order> findOrder = orderRepository.findById(order.getId());
        Assert.assertNotNull(findOrder);
    }
}
