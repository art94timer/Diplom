package repository;


import com.art.dip.model.Credential;
import com.art.dip.model.Person;
import com.art.dip.model.Role;
import com.art.dip.repository.PersonRepository;
import config.TestConfig;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;


@ExtendWith(SpringExtension.class)
@TestPropertySource(locations = "classpath:test.properties")
@SpringBootTest(classes = TestConfig.class)
public class PersonRepositoryTest {


    private final PersonRepository personRepository;


    @Autowired
    public PersonRepositoryTest(PersonRepository personRepository) {
        this.personRepository = personRepository;

    }

    @Test
    public void test() {
        Assertions.assertNotNull(personRepository);
    }

    @Test
    public void insertDuplicateEmail() {
        personRepository.saveAndFlush(new Person(null, "admiqwn", "art", LocalDate.now(), "admin@admin.by", new Credential(), Role.ADMIN, true));
        Assertions.assertThrows(DataIntegrityViolationException.class, () ->
                personRepository.saveAndFlush
                (new Person(null, "admirewqrqwn", "art",
                LocalDate.now(), "admin@admin.by", new Credential(), Role.ADMIN, true)));
    }

    @AfterEach
    @Transactional
    public void cleanUp() {
        personRepository.deleteAll();
    }

    @Test
    public void findByEmailTest() {
        Person admin = new Person();
        admin.setEmail("admin@admin.by");
        admin.setFirstName("admin");
        personRepository.save(admin);
        Person person = personRepository.findByEmail("admin@admin.by").orElse(null);
        Assertions.assertNotNull(person);
        Assertions.assertEquals(person.getFirstName(), "admin");
        Assertions.assertNull(personRepository.findByEmail("dsfadmin@admin.by").orElse(null));
    }


}
