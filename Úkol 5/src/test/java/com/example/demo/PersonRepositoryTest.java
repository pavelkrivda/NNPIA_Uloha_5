package com.example.demo;

import com.example.demo.entity.Address;
import com.example.demo.entity.Person;
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
public class PersonRepositoryTest {

    public static final String TEST_FIRST_MAME = "Pepa";
    public static final String TEST_LAST_NAME = "Novak";
    public static final int TEST_AGE = 5;
    @Autowired
    private PersonRepository personRepository;

    @Test
    public void savePersonTest() {
        Person person = new Person();
        person.setFirstName(TEST_FIRST_MAME);
        person.setLastName(TEST_LAST_NAME);
        person.setAge(TEST_AGE);

        personRepository.save(person);

        List<Person> all = personRepository.findAll();
        assertThat(all, hasSize(1));

        Person personFromDatabase = personRepository.findById(person.getId()).get();
        Assertions.assertThat(personFromDatabase.getFirstName()).isEqualTo(TEST_FIRST_MAME);
        Assertions.assertThat(personFromDatabase.getLastName()).isEqualTo(TEST_LAST_NAME);
        Assertions.assertThat(personFromDatabase.getAge()).isEqualTo(TEST_AGE);
    }

    @Test
    public void deletePersonTest() {
        Person person = new Person();
        person.setFirstName(TEST_FIRST_MAME);
        person.setLastName(TEST_LAST_NAME);
        person.setAge(TEST_AGE);
        personRepository.save(person);

        List<Person> all = personRepository.findAll();
        assertThat(all, hasSize(1));

        personRepository.delete(person);

        all = personRepository.findAll();
        assertThat(all, hasSize(0));
    }
}
