package com.example.demo.datafactory;

import com.example.demo.entity.Address;
import com.example.demo.entity.Person;
import com.example.demo.repository.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class PersonTestDataFactory {

    public static final String TEST_FIRST_NAME = "Test firstName";
    public static final String TEST_LAST_NAME = "Test lastName";
    public static final Integer TEST_AGE = 50;

    @Autowired
    private PersonRepository personRepository;

    @Autowired
    private AddressTestDataFactory addressTestDataFactory;

    private Person createDefaultPerson() {
        Person person = new Person();
        person.setFirstName(TEST_FIRST_NAME);
        person.setLastName(TEST_LAST_NAME);
        person.setAge(TEST_AGE);
        return person;
    }

    public Person getAndSavePerson() {
        Person person = createDefaultPerson();

        Address address = addressTestDataFactory.getAndSaveAddress();

        person.setAddress(address);
        personRepository.save(person);
        return person;
    }

    public void savePerson() {
        Person person = createDefaultPerson();

        Address address = addressTestDataFactory.getAndSaveAddress();

        person.setAddress(address);
        personRepository.save(person);
    }

    public void savePerson(String firstName, String lastName, Integer age, Address address) {
        Person person = new Person();
        person.setFirstName(firstName == null ? TEST_FIRST_NAME : firstName);
        person.setLastName(lastName == null ? TEST_LAST_NAME : lastName);
        person.setAge(age <= 0 || age >= 110 ? TEST_AGE : age);
        person.setAddress(address == null ? addressTestDataFactory.getAndSaveAddress() : address);
        personRepository.save(person);
    }

    public void savePerson(Person person) {
        if(person.getFirstName() == null) person.setFirstName(TEST_FIRST_NAME);
        if(person.getLastName() == null) person.setLastName(TEST_LAST_NAME);
        if(person.getAge() == null) person.setAge(TEST_AGE);
        if(person.getAddress() == null) person.setAddress(addressTestDataFactory.getAndSaveAddress());
        personRepository.save(person);
    }
}
