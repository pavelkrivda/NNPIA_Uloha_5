import com.example.demo.datafactory.AddressTestDataFactory
import com.example.demo.datafactory.PersonTestDataFactory
import com.example.demo.entity.Person
import com.example.demo.repository.PersonRepository
import org.assertj.core.api.Assertions
import org.junit.jupiter.api.Test
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import org.springframework.context.annotation.Import
import org.springframework.test.context.junit4.SpringRunner

import static org.hamcrest.MatcherAssert.assertThat
import static org.hamcrest.Matchers.hasSize

@RunWith(SpringRunner.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Import([PersonTestDataFactory.class, AddressTestDataFactory.class])
public class PersonRepositoryTest {

    @Autowired
    private PersonRepository personRepository;

    @Autowired
    private PersonTestDataFactory personTestDataFactory;

    @Test
    private void savePersonTest() {
        personTestDataFactory.savePerson();

        List<Person> all = personRepository.findAll();
        assertThat(all, hasSize(1));

        Person readFromDatabase = personRepository.findById(all.get(0).getId()).get();
        Assertions.assertThat(readFromDatabase.getFirstName()).isEqualTo(PersonTestDataFactory.TEST_FIRST_NAME);
        Assertions.assertThat(readFromDatabase.getLastName()).isEqualTo(PersonTestDataFactory.TEST_LAST_NAME);
        Assertions.assertThat(readFromDatabase.getAge()).isEqualTo(PersonTestDataFactory.TEST_AGE);

        Assertions.assertThat(readFromDatabase.getAddress().getCity()).isEqualTo(AddressTestDataFactory.TEST_CITY);
        Assertions.assertThat(readFromDatabase.getAddress().getState()).isEqualTo(AddressTestDataFactory.TEST_STATE);
        Assertions.assertThat(readFromDatabase.getAddress().getPostalCode()).isEqualTo(AddressTestDataFactory.TEST_POSTAL_CODE);
    }

    @Test
    private void deletePersonTest() {
        Person person = new Person(firstName: TEST_FIRST_NAME, lastName: TEST_LAST_NAME, age: TEST_AGE);
        personTestDataFactory.savePerson();

        List<Person> all = personRepository.findAll();
        assertThat(all, hasSize(1));

        personRepository.delete(all.get(0));

        all = personRepository.findAll();
        assertThat(all, hasSize(0));
    }
}
