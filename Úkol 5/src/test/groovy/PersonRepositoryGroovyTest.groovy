package com.example.demo;

import com.example.demo.datafactory.Creator;
import com.example.demo.entity.Person;
import com.example.demo.repository.PersonRepository;
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
public class PersonRepositoryGroovyTest {

    public static final String TEST_FIRST_NAME = "Test firstName";
    public static final String TEST_LAST_NAME = "Test lastName";
    public static final Integer TEST_AGE = 0;
    public static final String TEST_CITY = "Test city";
    public static final String TEST_STATE = "Test state";
    public static final int TEST_POSLAT_CODE = 0;

    @Autowired
    private Creator creator;

    @Autowired
    private PersonRepository personRepository;

    @Test
    public void savePersonTest() {
        Person person = new Person(firstName: TEST_FIRST_NAME, lastName: TEST_LAST_NAME, age: TEST_AGE);
        creator.save(person);

        List<Person> all = personRepository.findAll();
        assertThat(all, hasSize(1))

        Person readFromDatabase = personRepository.findById(person.getId()).get();
        Assertions.assertThat(readFromDatabase.firstName).isEqualTo(TEST_FIRST_NAME);
        Assertions.assertThat(readFromDatabase.lastName).isEqualTo(TEST_LAST_NAME);
        Assertions.assertThat(readFromDatabase.age).isEqualTo(TEST_AGE);
        Assertions.assertThat(readFromDatabase.getAddress().city).isEqualTo(TEST_CITY);
        Assertions.assertThat(readFromDatabase.getAddress().state).isEqualTo(TEST_STATE);
        Assertions.assertThat(readFromDatabase.getAddress().postalCode).isEqualTo(TEST_POSLAT_CODE);
    }

    @Test
    public void deletePersonTest() {
        Person person = new Person(firstName: TEST_FIRST_NAME, lastName: TEST_LAST_NAME, age: TEST_AGE);
        creator.save(person);

        List<Person> all = personRepository.findAll();
        assertThat(all, hasSize(1));

        personRepository.delete(person);

        all = personRepository.findAll();
        assertThat(all, hasSize(0));
    }
}
