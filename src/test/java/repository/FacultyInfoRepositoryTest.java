package repository;

import com.art.dip.model.Applicant;
import com.art.dip.model.Faculty;
import com.art.dip.model.FacultyInfo;
import com.art.dip.repository.ApplicantRepository;
import com.art.dip.repository.FacultyInfoRepository;
import com.art.dip.repository.FacultyRepository;
import com.art.dip.utility.dto.UpdateFacultyDTO;
import config.TestConfig;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

@ExtendWith(SpringExtension.class)
@TestPropertySource(locations = "classpath:test.properties")
@SpringBootTest(classes = TestConfig.class)
public class FacultyInfoRepositoryTest {


    private final FacultyInfoRepository repository;

    private final FacultyRepository facultyRepository;

    private final ApplicantRepository applicantRepository;

    private final FacultyInfoRepository facultyInfoRepository;

    @Autowired
    public FacultyInfoRepositoryTest(FacultyInfoRepository repository, FacultyRepository facultyRepository, ApplicantRepository applicantRepository, FacultyInfoRepository facultyInfoRepository) {
        this.repository = repository;
        this.facultyRepository = facultyRepository;
        this.applicantRepository = applicantRepository;
        this.facultyInfoRepository = facultyInfoRepository;
    }

    @Test
    public void start() {
        Assertions.assertNotNull(repository);
    }

    @BeforeEach
    @Transactional
    public void save() {
        Faculty faculty = new Faculty();
        faculty.setName("faculty");
        faculty.setRuName("Факультет");
        FacultyInfo facultyInfo = new FacultyInfo();
        facultyInfo.setCountApplicants(10);
        facultyInfo.setAvailable(true);
        facultyInfo.setAverageScore(10d);
        faculty.setInfo(facultyInfo);
        facultyRepository.saveAndFlush(faculty);
        Applicant applicant = new Applicant();
        applicant.setFaculty(faculty);
        applicant.setScore(100);
        applicantRepository.save(applicant);
    }
    @AfterEach
    @Transactional
    public void clear() {
        facultyInfoRepository.deleteAll();
        applicantRepository.deleteAll();
        facultyRepository.deleteAll();
        repository.deleteAll();
    }

    @Test
    public void testSchedulingTask() {
        Faculty faculty = facultyRepository.findById(1000).orElse(null);
        Assertions.assertNotNull(faculty);
        UpdateFacultyDTO updateFacultyDTO = repository.updateFacultyInfo(faculty.getId());
        Assertions.assertNotNull(updateFacultyDTO);
        Assertions.assertEquals(updateFacultyDTO.getAvg(), 100d);
        Assertions.assertEquals((long) updateFacultyDTO.getCount(), 1L);
    }
}
