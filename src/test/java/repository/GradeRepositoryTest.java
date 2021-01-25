package repository;

import com.art.dip.model.Applicant;
import com.art.dip.model.Grade;
import com.art.dip.model.Subject;
import com.art.dip.repository.ApplicantRepository;
import com.art.dip.repository.GradeRepository;
import com.art.dip.repository.SubjectRepository;
import com.art.dip.utility.dto.ValidateGradeDTO;
import config.TestConfiguration;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;


@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = TestConfiguration.class)
@DataJpaTest
public class GradeRepositoryTest {

    private final SubjectRepository subjectRepository;

    private final GradeRepository repository;

    private final ApplicantRepository applicantRepository;

    @Autowired
    public GradeRepositoryTest(SubjectRepository subjectRepository, GradeRepository repository, ApplicantRepository applicantRepository) {
        this.subjectRepository = subjectRepository;
        this.repository = repository;
        this.applicantRepository = applicantRepository;
    }


    @Test
    public void start() {
        Assertions.assertNotNull(repository);
    }

    @Test
    @Transactional
    public void getRuGradesByApplicant() {
        Grade grade = new Grade();
        grade.setMark(10);
        grade.setFileName("dsgdfgdfsg");
        Subject subject = new Subject();
        subject.setFaculties(Collections.emptyList());
        subject.setRuName("fsdfs");
        subjectRepository.save(subject);
        grade.setSubject(subject);
        Applicant applicant = new Applicant();
        applicantRepository.saveAndFlush(applicant);
        repository.save(grade);
        grade.setApplicant(applicant);
        System.out.println(applicant.getId());
//        List<ValidateGradeDTO> ruGradesForApplicant = repository.getRuGradesForApplicant(applicant.getId());
//        Assertions.assertNotNull(ruGradesForApplicant);
//        Assertions.assertFalse(ruGradesForApplicant.isEmpty());
    }

    @Test
    @Transactional
    public void getEnGradesByApplicant() {
        Grade grade = new Grade();
        grade.setMark(10);
        grade.setFileName("dsgdfgdfsg");
        Subject subject = new Subject();
        subject.setFaculties(Collections.emptyList());
        subject.setName("fsdfs");
        subjectRepository.save(subject);
        grade.setSubject(subject);
        Applicant applicant = new Applicant();
        applicantRepository.saveAndFlush(applicant);
        repository.save(grade);
        grade.setApplicant(applicant);
        System.out.println(applicant.getId());
        //List<ValidateGradeDTO> ruGradesForApplicant = repository.getRuGradesForApplicant(applicant.getId());
     //   Assertions.assertNotNull(ruGradesForApplicant);
      //  Assertions.assertFalse(ruGradesForApplicant.isEmpty());
    }

}
