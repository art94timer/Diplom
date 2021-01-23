package repository;

import com.art.dip.model.Faculty;
import com.art.dip.model.FacultyInfo;
import com.art.dip.repository.FacultyInfoRepository;
import com.art.dip.repository.FacultyRepository;
import com.art.dip.utility.dto.FacultyDTO;
import com.art.dip.utility.dto.FacultyInfoDTO;
import config.TestConfiguration;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.jpa.repository.Query;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDateTime;
import java.util.List;

import static com.art.dip.utility.Constants.PATH_TO_FACULTYDTO;
import static com.art.dip.utility.Constants.PATH_TO_FACULTY_INFO_DTO;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = TestConfiguration.class)
@DataJpaTest
public class FacultyRepositoryTest {

    private final FacultyRepository repository;

    private final FacultyInfoRepository infoRepository;

    @Autowired
    public FacultyRepositoryTest(FacultyRepository repository, FacultyInfoRepository infoRepository) {
        this.repository = repository;
        this.infoRepository = infoRepository;
    }

    @Test
    public void start() {
        Assertions.assertNotNull(repository);
    }
    @BeforeEach
    public void startUp() {
        Faculty faculty = new Faculty();
        faculty.setRuName("dsd");
        faculty.setName("fdsfa");
        repository.saveAndFlush(faculty);
        FacultyInfo info = new FacultyInfo();
        info.setExpiredDate(LocalDateTime.now().plusDays(1));
        info.setFaculty(repository.findById(faculty.getId()).get());
        infoRepository.save(info);

    }

    @Test
    public void getAllRuFaculties() {

        List<FacultyDTO> allFaculties = repository.getAllRuFaculties();
        Assertions.assertNotNull(allFaculties);
        Assertions.assertFalse(allFaculties.isEmpty());
    }

    @Test
    public void getFacultyById() {

        FacultyDTO faculty = repository.getRuFacultyById(2);
        Assertions.assertNotNull(faculty);
    }

    @Test
    public void getFacultiesIsNotExpired() {
      Faculty faculty=  repository.findById(1).get();
        repository.save(faculty);
        List<FacultyDTO> facultiesIsNotExpired = repository.getRuFacultiesIsNotExpired(LocalDateTime.now());
        Assertions.assertNotNull(facultiesIsNotExpired);
        Assertions.assertFalse(facultiesIsNotExpired.isEmpty());
    }


    @Test
    public void getFacultyWithInfoById() {
        FacultyInfoDTO facultyInfoDTO = repository.getRuFacultyWithInfoById(3);
        Assertions.assertNotNull(facultyInfoDTO);
    }
}
