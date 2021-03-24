package com.example.demo;

import com.example.demo.entity.Address;
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
public class AdressRepositoryTest {

    private static final String TEST_CITY = "Praha";
    private static final String TEST_STATE = "Czech Republic";
    private static final int TEST_POSTAL_CODE = 54924;

    @Autowired
    private AddressRepository addressRepository;

    @Test
    public void saveAddressTest() {
        Address address = new Address();
        address.setCity(TEST_CITY);
        address.setState(TEST_STATE);
        address.setPostalCode(TEST_POSTAL_CODE);
        addressRepository.save(address);

        List<Address> all = addressRepository.findAll();
        assertThat(all, hasSize(1));

        Address addressFromDatabase = addressRepository.findById(address.getId()).get();
        Assertions.assertThat(addressFromDatabase.getCity()).isEqualTo(TEST_CITY);
        Assertions.assertThat(addressFromDatabase.getState()).isEqualTo(TEST_STATE);
        Assertions.assertThat(addressFromDatabase.getPostalCode()).isEqualTo(TEST_POSTAL_CODE);
    }

    @Test
    public void deleteAddressTest() {
        Address address = new Address();
        address.setCity(TEST_CITY);
        address.setState(TEST_STATE);
        address.setPostalCode(TEST_POSTAL_CODE);
        addressRepository.save(address);

        List<Address> all = addressRepository.findAll();
        assertThat(all, hasSize(1));

        addressRepository.delete(address);

        all = addressRepository.findAll();
        assertThat(all, hasSize(0));
    }
}
