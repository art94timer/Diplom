package controller;

import com.art.dip.model.Faculty;
import com.art.dip.repository.FacultyRepository;
import config.TestConfig;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes={TestConfig.class})
@TestPropertySource(locations = "classpath:test.properties")
@AutoConfigureMockMvc
public class ApplicantControllerTest {


    private final MockMvc mock;

    private final FacultyRepository facultyRepository;

    @Autowired
    public ApplicantControllerTest(MockMvc mock, FacultyRepository facultyRepository) {
        this.mock = mock;
        this.facultyRepository = facultyRepository;
    }

    @BeforeEach
    public void setup() {
        Faculty faculty = new Faculty();
        faculty.setName("Faculty");
        faculty.setRuName("Факультет");
        facultyRepository.save(faculty);

    }
    @AfterEach
    public void cleanUp() {
        facultyRepository.deleteAll();
    }

    @Test
    public void start() {
        Assertions.assertNotNull(mock);
    }

    @Test
    @WithMockUser(username = "user")
    public void testForRedirectIfFacultyIdIsNull() throws Exception {
        mock.perform(get("/applicant/faculty")).andExpect(status().is3xxRedirection());
    }

    @Test
    @WithMockUser(username = "user")
    public void testForRedirectIfFacultyIdIsNotNull() throws Exception {
        mock.perform(get("/applicant/faculty").param("facId",String.valueOf(1000))).andExpect(status().isOk());
    }



}
