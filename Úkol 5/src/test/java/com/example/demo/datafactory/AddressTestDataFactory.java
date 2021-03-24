package com.example.demo.datafactory;

import com.example.demo.entity.Address;
import com.example.demo.repository.AddressRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class AddressTestDataFactory {

    public static final String TEST_CITY = "Test city";
    public static final String TEST_STATE = "Test state";
    public static final int TEST_POSTAL_CODE = 11111;

    @Autowired
    private AddressRepository addressRepository;

    private Address createDefaultAddress() {
        Address address = new Address();
        address.setCity(TEST_CITY);
        address.setState(TEST_STATE);
        address.setPostalCode(TEST_POSTAL_CODE);
        return address;
    }

    public Address getAndSaveAddress() {
        Address address = createDefaultAddress();
        addressRepository.save(address);
        return address;
    }

    public void saveAddress() {
        Address address = createDefaultAddress();
        addressRepository.save(address);
    }

    public void saveAddress(Address address) {
        if(address.getCity() == null) address.setCity(TEST_CITY);
        if(address.getState() == null) address.setCity(TEST_STATE);
        if(address.getPostalCode().toString().length() != 5) address.setPostalCode(TEST_POSTAL_CODE);
        addressRepository.save(address);
    }

    public void saveAddress(String city, String state, int postalCode) {
        Address address = new Address();
        address.setCity(city == null ? TEST_CITY : address.getCity());
        address.setState(state == null ? TEST_STATE : address.getState());
        address.setPostalCode(Integer.toString(postalCode).length() == 5 ? TEST_POSTAL_CODE : address.getPostalCode());
        addressRepository.save(address);
    }
}
