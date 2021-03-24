package com.example.demo;

import com.example.demo.datafactory.Creator;
import com.example.demo.entity.Address
import com.example.demo.repository.AddressRepository
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
public class AdressRepositoryGroovyTest {

    public static final String TEST_CITY = "Prague";
    public static final String TEST_STATE = "Czech Republic";
    public static final int TEST_POSLAT_CODE = 12345;

    @Autowired
    private Creator creator;

    @Autowired
    private AddressRepository addressRepository;

    @Test
    public void saveAddressTest() {
        Address address = new Address(city: TEST_CITY, state: TEST_STATE, postalCode: TEST_POSLAT_CODE);
        creator.save(address);
        List<Address> all = addressRepository.findAll();
        assertThat(all, hasSize(1));

        Address readFromDatabase = addressRepository.findById(address.getId()).get();
        Assertions.assertThat(readFromDatabase.city).isEqualTo(TEST_CITY);
        Assertions.assertThat(readFromDatabase.state).isEqualTo(TEST_STATE);
        Assertions.assertThat(readFromDatabase.postalCode).isEqualTo(TEST_POSLAT_CODE);
    }

    @Test
    public void deleteAddressTest() {
        Address address = new Address(city: TEST_CITY, state: TEST_STATE, postalCode: TEST_POSLAT_CODE);
        creator.save(address);

        List<Address> all = addressRepository.findAll();
        assertThat(all, hasSize(1));

        addressRepository.delete(all.get(0));

        all = addressRepository.findAll();
        assertThat(all, hasSize(0));
    }
}
