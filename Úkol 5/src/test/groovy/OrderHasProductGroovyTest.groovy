package com.example.demo;

import com.example.demo.datafactory.*
import com.example.demo.entity.Address
import com.example.demo.entity.Order;
import com.example.demo.entity.OrderHasProduct
import com.example.demo.entity.Product
import com.example.demo.entity.State;
import com.example.demo.repository.OrderHasProductRepository
import org.assertj.core.api.Assertions;
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
public class OrderHasProductGroovyTest {

    public static final int TEST_AMOUNT = 0
    public static final State TEST_STATE = State.NEW;
    public static final String TEST_FIRST_NAME = "Test firstName";
    public static final String TEST_LAST_NAME = "Test lastName";
    public static final int TEST_AGE = 0;
    public static final String TEST_CITY = "Test city";
    public static final String TEST_ADDRESS_STATE = "Test state";
    public static final int TEST_POSLAT_CODE = 0;
    public static final String TEST_PRODUCT = "Test name";
    public static final String TEST_DESCRIPTION = "Test description";
    public static final String TEST_SUPPLIER = "Test name";

    @Autowired
    private Creator creator;

    @Autowired
    private OrderHasProductRepository orderHasProductRepository;

    @Test
    public void saveOrderHasProductTest() {
        OrderHasProduct orderHasProduct = new OrderHasProduct(amount: TEST_AMOUNT);
        creator.save(orderHasProduct);

        List<OrderHasProduct> all = orderHasProductRepository.findAll();
        assertThat(all, hasSize(1));

        OrderHasProduct readFromDatabase = orderHasProductRepository.findById(orderHasProduct.getId()).get();
        Assertions.assertThat(readFromDatabase.getAmount()).isEqualTo(TEST_AMOUNT);

        Assertions.assertThat(readFromDatabase.getOrder().state).isEqualTo(TEST_STATE);
        Assertions.assertThat(readFromDatabase.getOrder().getPerson().firstName).isEqualTo(TEST_FIRST_NAME);
        Assertions.assertThat(readFromDatabase.getOrder().getPerson().lastName).isEqualTo(TEST_LAST_NAME);
        Assertions.assertThat(readFromDatabase.getOrder().getPerson().age).isEqualTo(TEST_AGE);
        Assertions.assertThat(readFromDatabase.getOrder().getPerson().address.city).isEqualTo(TEST_CITY);
        Assertions.assertThat(readFromDatabase.getOrder().getPerson().address.state).isEqualTo(TEST_ADDRESS_STATE);
        Assertions.assertThat(readFromDatabase.getOrder().getPerson().address.postalCode).isEqualTo(TEST_POSLAT_CODE);

        Assertions.assertThat(readFromDatabase.getProduct().getName()).isEqualTo(TEST_PRODUCT);
        Assertions.assertThat(readFromDatabase.getProduct().getDescription()).isEqualTo(TEST_DESCRIPTION);
        Assertions.assertThat(readFromDatabase.getProduct().getSupplier().getName()).isEqualTo(TEST_SUPPLIER);
    }

    @Test
    public void deleteOrderHasProductTest() {
        OrderHasProduct orderHasProduct = new OrderHasProduct(amount: TEST_AMOUNT);
        creator.save(orderHasProduct);

        List<OrderHasProduct> all = orderHasProductRepository.findAll();
        assertThat(all, hasSize(1));

        orderHasProductRepository.delete(orderHasProduct);

        all = orderHasProductRepository.findAll();
        assertThat(all, hasSize(0));
    }
}
