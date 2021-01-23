package repository;

import com.art.dip.repository.FacultyInfoRepository;
import config.TestConfiguration;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = TestConfiguration.class)
@DataJpaTest
public class FacultyInfoRepositoryTest {

    private final FacultyInfoRepository repository;


    public FacultyInfoRepositoryTest(FacultyInfoRepository repository) {
        this.repository = repository;
    }

    @Test
    public void start() {
        Assertions.assertNotNull(repository);
    }
}
