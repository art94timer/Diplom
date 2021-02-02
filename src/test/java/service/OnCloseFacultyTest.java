package service;

import com.art.dip.model.*;
import com.art.dip.repository.ApplicantRepository;
import com.art.dip.repository.FacultyInfoRepository;
import com.art.dip.repository.FacultyRepository;
import com.art.dip.repository.PersonRepository;
import com.art.dip.schedule.ApplicantEmailSendRunnableTask;
import com.art.dip.service.interfaces.EmailService;
import config.TestConfig;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.Locale;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = TestConfig.class)
@TestPropertySource(locations = "classpath:test.properties")
@SpringBootTest
public class OnCloseFacultyTest {

    private final ApplicantRepository applicantRepository;

    private final FacultyInfoRepository facultyInfoRepository;

    private final EmailService emailService;

    private final PersonRepository personRepository;

    private final FacultyRepository facultyRepository;


    private final ScheduledExecutorService ses = Executors.newScheduledThreadPool(100);

    @Autowired
    public OnCloseFacultyTest(ApplicantRepository applicantRepository, FacultyInfoRepository facultyInfoRepository, EmailService emailService, PersonRepository personRepository, FacultyRepository facultyRepository) {
        this.applicantRepository = applicantRepository;
        this.facultyInfoRepository = facultyInfoRepository;
        this.emailService = emailService;
        this.personRepository = personRepository;
        this.facultyRepository = facultyRepository;
    }

    @BeforeEach
    public void createPerson() {
        Person person = new Person();
        person.setEmail("timerbaev9411121212211212@bk.ru");
        person.setLocale(new Locale("ru"));
        person.setFirstName("Artem");
        person.setLastName("Timerbaev");
        person.setEnabled(true);
        person.setBirthdate(LocalDate.now().minusDays(1));
        person.setRole(Role.USER);
        Credential credential = new Credential();
        credential.setPassword("pass");
        person.setCredential(credential);
        personRepository.save(person);
        Faculty faculty = new Faculty();
        faculty.setName("faculty");
        faculty.setRuName("Факультет");
        faculty.setSubjects(Collections.emptyList());
        faculty.setNotifyHolder(new NotifyHolder());
        FacultyInfo facultyInfo = new FacultyInfo();
        facultyInfo.setAvailable(true);
        facultyInfo.setAverageScore(0d);
        facultyInfo.setCountApplicants(0);
        facultyInfo.setExpiredDate(LocalDateTime.now().plusDays(1));
        facultyInfo.setCapacity(10);
        facultyInfo.setFaculty(faculty);
        faculty.setInfo(facultyInfo);
        facultyRepository.save(faculty);
        Applicant applicant = new Applicant();
        applicant.setPerson(person);
        applicant.setFaculty(faculty);
        applicant.setScore(100);
        applicant.setRegistrationTime(LocalDateTime.now());
        applicant.setIsAccepted(true);
        applicantRepository.save(applicant);
    }


    @Test
    public void testOnCloseFaculty() throws InterruptedException {
       Faculty f =  facultyRepository.findById(1002).orElse(null);
       Assertions.assertNotNull(f);
        FacultyInfo facultyInfo = facultyInfoRepository.findByFaculty_Id(1);
        f.setInfo(facultyInfo);
        ses.schedule(new ApplicantEmailSendRunnableTask(f, emailService, applicantRepository, facultyInfoRepository), 5, TimeUnit.SECONDS);
        Thread.sleep(1000 * 6);
    }
}
