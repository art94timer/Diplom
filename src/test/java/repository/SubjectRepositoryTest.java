package repository;

import com.art.dip.model.Faculty;
import com.art.dip.model.Subject;
import com.art.dip.repository.FacultyRepository;
import com.art.dip.repository.SubjectRepository;
import com.art.dip.utility.dto.SubjectDTO;
import config.TestConfiguration;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = TestConfiguration.class)
@DataJpaTest
public class SubjectRepositoryTest {

    private final FacultyRepository facultyRepository;

    private final SubjectRepository repository;

    @Autowired
    public SubjectRepositoryTest(FacultyRepository facultyRepository, SubjectRepository repository) {
        this.facultyRepository = facultyRepository;
        this.repository = repository;
    }

    @Test
    public void start() {
        Assertions.assertNotNull(facultyRepository);
        Assertions.assertNotNull(repository);
    }


    @Test
    public void findRuSubjectsByFacultyId() {
        Faculty faculty = new Faculty();
        faculty.setName("art");

        facultyRepository.save(faculty);
        Subject subject = new Subject();
        subject.setRuName("tim");
        subject.setFaculties(Collections.singletonList(faculty));
        repository.saveAndFlush(subject);
        faculty.setSubjects(Collections.singletonList(subject));

//        List<SubjectDTO> enSubjects = repository.findEnSubjectsByFacultyId(faculty.getId());
//        Assertions.assertNotNull(enSubjects);
//        Assertions.assertFalse(enSubjects.isEmpty());

    }
}
