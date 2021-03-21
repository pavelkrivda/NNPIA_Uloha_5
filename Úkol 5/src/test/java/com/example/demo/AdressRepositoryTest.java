package com.example.demo;

import com.example.demo.entity.Address;
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
public class AdressRepositoryTest {


    @Autowired
    private AddressRepository addressRepository;

    @Test
    public void saveAddressTest() {
        Address address = new Address();
        address.setCity("Praha");
        address.setState("Czech Republic");
        address.setPostalCode(54924);
        addressRepository.save(address);

        List<Address> all = addressRepository.findAll();
        assertThat(all, hasSize(1));
    }

    @Test
    public void deleteAddressTest() {
        Address address = new Address();
        address.setCity("Praha");
        address.setState("Czech Republic");
        address.setPostalCode(54924);
        addressRepository.save(address);

        List<Address> all = addressRepository.findAll();
        assertThat(all, hasSize(1));

        addressRepository.delete(address);

        all = addressRepository.findAll();
        assertThat(all, hasSize(0));
    }
}
