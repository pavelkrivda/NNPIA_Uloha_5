package com.example.demo;

import com.example.demo.datafactory.*;
import com.example.demo.entity.OrderHasProduct;
import com.example.demo.repository.*;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasSize;

@RunWith(SpringRunner.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Import({OrderHasProductTestDataFactory.class, OrderTestDataFactory.class, PersonTestDataFactory.class, AddressTestDataFactory.class,  ProductTestDataFactory.class, SupplierTestDataFactory.class})
public class OrderHasProductTest {

    @Autowired
    private OrderHasProductRepository orderHasProductRepository;

    @Autowired
    private OrderHasProductTestDataFactory orderHasProductTestDataFactory;

    @Test
    public void saveOrderHasProductTest() {
        orderHasProductTestDataFactory.saveOrderHasProduct();

        List<OrderHasProduct> all = orderHasProductRepository.findAll();
        assertThat(all, hasSize(1));

        OrderHasProduct readFromDatabase = orderHasProductRepository.findById(all.get(0).getId()).get();
        Assertions.assertThat(readFromDatabase.getAmount()).isEqualTo(OrderHasProductTestDataFactory.TEST_AMOUNT);

        Assertions.assertThat(readFromDatabase.getOrder().getState()).isEqualTo(OrderTestDataFactory.TEST_STATE);

        Assertions.assertThat(readFromDatabase.getOrder().getPerson().getFirstName()).isEqualTo(PersonTestDataFactory.TEST_FIRST_NAME);
        Assertions.assertThat(readFromDatabase.getOrder().getPerson().getLastName()).isEqualTo(PersonTestDataFactory.TEST_LAST_NAME);
        Assertions.assertThat(readFromDatabase.getOrder().getPerson().getAge()).isEqualTo(PersonTestDataFactory.TEST_AGE);

        Assertions.assertThat(readFromDatabase.getOrder().getPerson().getAddress().getCity()).isEqualTo(AddressTestDataFactory.TEST_CITY);
        Assertions.assertThat(readFromDatabase.getOrder().getPerson().getAddress().getState()).isEqualTo(AddressTestDataFactory.TEST_STATE);
        Assertions.assertThat(readFromDatabase.getOrder().getPerson().getAddress().getPostalCode()).isEqualTo(AddressTestDataFactory.TEST_POSTAL_CODE);

        Assertions.assertThat(readFromDatabase.getProduct().getName()).isEqualTo(ProductTestDataFactory.TEST_PRODUCT);
        Assertions.assertThat(readFromDatabase.getProduct().getDescription()).isEqualTo(ProductTestDataFactory.TEST_DESCRIPTION);
        Assertions.assertThat(readFromDatabase.getProduct().getSupplier().getName()).isEqualTo(SupplierTestDataFactory.TEST_SUPPLIER);
    }

    @Test
    public void deleteOrderHasProductTest() {
        orderHasProductTestDataFactory.saveOrderHasProduct();

        List<OrderHasProduct> all = orderHasProductRepository.findAll();
        assertThat(all, hasSize(1));

        orderHasProductRepository.delete(all.get(0));

        all = orderHasProductRepository.findAll();
        assertThat(all, hasSize(0));
    }
}
