package com.example.demo;

import com.example.demo.entity.Person;
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
public class PersonRepositoryTest {

    @Autowired
    private PersonRepository personRepository;

    @Test
    public void savePersonTest() {
        Person person = new Person();
        person.setFirstName("Pepa");
        person.setLastName("Novak");
        person.setAge((byte)5);

        personRepository.save(person);

        List<Person> all = personRepository.findAll();
        assertThat(all, hasSize(1));
    }

    @Test
    public void deletePersonTest() {
        Person person = new Person();
        person.setFirstName("Pepa");
        person.setLastName("Novak");
        person.setAge((byte)5);
        personRepository.save(person);

        List<Person> all = personRepository.findAll();
        assertThat(all, hasSize(1));

        personRepository.delete(person);

        all = personRepository.findAll();
        assertThat(all, hasSize(0));
    }
}
