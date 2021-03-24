package com.example.demo;

import com.example.demo.datafactory.AddressTestDataFactory;
import com.example.demo.datafactory.OrderTestDataFactory;
import com.example.demo.datafactory.PersonTestDataFactory;
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
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;
import java.util.Optional;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasSize;

@RunWith(SpringRunner.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Import({OrderTestDataFactory.class, PersonTestDataFactory.class, AddressTestDataFactory.class})
public class OrderRepositoryTest {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private OrderTestDataFactory orderTestDataFactory;

    @Test
    public void saveOrderTest() {
        orderTestDataFactory.saveOrder();

        List<Order> all = orderRepository.findAll();
        assertThat(all, hasSize(1));

        Order readFromDatabase = orderRepository.findById(all.get(0).getId()).get();

        Assertions.assertThat(readFromDatabase.getState()).isEqualTo(OrderTestDataFactory.TEST_STATE);

        Assertions.assertThat(readFromDatabase.getPerson().getFirstName()).isEqualTo(PersonTestDataFactory.TEST_FIRST_NAME);
        Assertions.assertThat(readFromDatabase.getPerson().getLastName()).isEqualTo(PersonTestDataFactory.TEST_LAST_NAME);
        Assertions.assertThat(readFromDatabase.getPerson().getAge()).isEqualTo(PersonTestDataFactory.TEST_AGE);

        Assertions.assertThat(readFromDatabase.getPerson().getAddress().getCity()).isEqualTo(AddressTestDataFactory.TEST_CITY);
        Assertions.assertThat(readFromDatabase.getPerson().getAddress().getState()).isEqualTo(AddressTestDataFactory.TEST_STATE);
        Assertions.assertThat(readFromDatabase.getPerson().getAddress().getPostalCode()).isEqualTo(AddressTestDataFactory.TEST_POSTAL_CODE);
    }

    @Test
    public void deleteOrderTest() {
        orderTestDataFactory.saveOrder();

        List<Order> all = orderRepository.findAll();
        assertThat(all, hasSize(1));

        orderRepository.delete(all.get(0));

        all = orderRepository.findAll();
        assertThat(all, hasSize(0));
    }

    @Test
    public void findByIdTest() {
        orderTestDataFactory.saveOrder();

        Optional<Order> findOrder = orderRepository.findById(0L);
        Assert.assertNotNull(findOrder);
    }
}
