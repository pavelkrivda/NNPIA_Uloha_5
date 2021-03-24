package com.example.demo;

import com.example.demo.datafactory.AddressTestDataFactory;
import com.example.demo.entity.Address;
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
@Import(AddressTestDataFactory.class)
public class AdressRepositoryTest {

    @Autowired
    private AddressRepository addressRepository;

    @Autowired
    private AddressTestDataFactory addressTestDataFactory;

    @Test
    public void saveAddressTest() {
        addressTestDataFactory.saveAddress();

        List<Address> all = addressRepository.findAll();
        assertThat(all, hasSize(1));

        Address readFromDatabase = addressRepository.findById(all.get(0).getId()).get();
        Assertions.assertThat(readFromDatabase.getCity()).isEqualTo(AddressTestDataFactory.TEST_CITY);
        Assertions.assertThat(readFromDatabase.getState()).isEqualTo(AddressTestDataFactory.TEST_STATE);
        Assertions.assertThat(readFromDatabase.getPostalCode()).isEqualTo(AddressTestDataFactory.TEST_POSTAL_CODE);
    }

    @Test
    public void deleteAddressTest() {
        addressTestDataFactory.saveAddress();

        List<Address> all = addressRepository.findAll();
        assertThat(all, hasSize(1));

        addressRepository.delete(all.get(0));

        all = addressRepository.findAll();
        assertThat(all, hasSize(0));
    }
}
