package repository;


import com.art.dip.model.Credential;
import com.art.dip.model.Person;
import com.art.dip.model.Role;
import com.art.dip.repository.PersonRepository;
import config.TestConfiguration;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDate;

import static repository.testData.PersonData.ADMIN;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = TestConfiguration.class)
@DataJpaTest
public class PersonRepositoryTest {

    @Autowired
    private PersonRepository personRepository;

    @Test
    public void test() {
        Assertions.assertNotNull(personRepository);
    }

    @Test
    public void insertDuplicateEmail() {
        personRepository.saveAndFlush(new Person(null, "admiqwn", "art", LocalDate.now(), "admin@admin.by", new Credential(), Role.ADMIN, true));
        Assertions.assertThrows(DataIntegrityViolationException.class, () -> {
            personRepository.saveAndFlush(new Person(null, "admirewqrqwn", "art",
                    LocalDate.now(), "admin@admin.by", new Credential(), Role.ADMIN, true));
        });
    }

    @Test
    public void findByEmailTest() {
        personRepository.save(ADMIN);
        Person person = personRepository.findByEmail("admin@admin.by").orElse(null);
        Assertions.assertNotNull(person);
        Assertions.assertEquals(person.getFirstName(), "admin");
        Assertions.assertNull(personRepository.findByEmail("dsfadmin@admin.by").orElse(null));
    }


}
