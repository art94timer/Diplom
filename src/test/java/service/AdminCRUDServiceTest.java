package service;

import com.art.dip.model.Faculty;
import com.art.dip.model.FacultyInfo;
import com.art.dip.model.Subject;
import com.art.dip.repository.FacultyRepository;
import com.art.dip.repository.SubjectRepository;
import com.art.dip.service.interfaces.AdminCRUDService;
import com.art.dip.utility.exception.FacultyCRUDException;
import com.art.dip.utility.exception.SubjectCRUDException;
import config.TestConfig;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.support.DataAccessUtils;
import org.springframework.data.domain.Example;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.ArrayList;
import java.util.Collections;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = TestConfig.class)
@TestPropertySource(locations = "classpath:test.properties")
@SpringBootTest
public class AdminCRUDServiceTest {

    private final AdminCRUDService service;

    private final FacultyRepository facultyRepository;

    private final SubjectRepository subjectRepository;


    @Autowired
    public AdminCRUDServiceTest(AdminCRUDService service, FacultyRepository facultyRepository, SubjectRepository subjectRepository) {
        this.service = service;
        this.facultyRepository = facultyRepository;
        this.subjectRepository = subjectRepository;
    }

    @BeforeEach
    public void saveAll() {
        Subject subject = new Subject();
        Faculty faculty = new Faculty();
        faculty.setName("faculty");
        faculty.setRuName("Факультет");
        faculty.setInfo(new FacultyInfo());
        service.createOrEditFaculty(faculty);
        subject.setFaculties(Collections.singletonList(faculty));
        subject.setName("Math");
        subject.setRuName("Математика");
        service.createOrEditSubject(subject);
    }


    @AfterEach
    public void cleanUp() {
        subjectRepository.deleteAll();
        facultyRepository.deleteAll();

    }

    @Test
    public void createOrEditTest() {
        Subject duplicate = new Subject();
        duplicate.setRuName("Дубликат");
        duplicate.setName("Math");
        Assertions.assertThrows(SubjectCRUDException.class, () -> service.createOrEditSubject(duplicate));
    }

    @Test
    public void deleteSubjectWithNotEmptyFacultiesTest() {
        Subject subject = new Subject();
        subject.setName("Math");
        Subject find = DataAccessUtils.singleResult(subjectRepository.findAll(Example.of(subject)));
        Assertions.assertNotNull(find);
        Assertions.assertThrows(SubjectCRUDException.class, () -> service.deleteSubject(find.getId()));
    }


    @Test
    public void openRecruitingWithEmptySubjects() {
        Faculty faculty = new Faculty();
        faculty.setName("faculty");
        Faculty find = DataAccessUtils.singleResult(facultyRepository.findAll(Example.of(faculty)));
        Assertions.assertNotNull(find);
        find.setSubjects(new ArrayList<>());
        Assertions.assertThrows(FacultyCRUDException.class,()->service.openRecruiting(find.getInfo()));
    }

}
