package com.art.dip.schedule;

import com.art.dip.model.Applicant;
import com.art.dip.model.Faculty;
import com.art.dip.model.FacultyInfo;
import com.art.dip.repository.ApplicantRepository;
import com.art.dip.repository.FacultyInfoRepository;
import com.art.dip.service.interfaces.EmailService;
import lombok.AllArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
@AllArgsConstructor
public class ApplicantEmailSendRunnableTask implements Runnable {

    private final Faculty faculty;

    private final EmailService emailService;

    private final ApplicantRepository repository;

    private final FacultyInfoRepository facultyInfoRepository;

    @Override
    public void run() {
        List<Applicant> applicants = repository.getAllApplicantWithPersonForAcceptedOrNot(faculty.getId());
        if (applicants.size() > faculty.getInfo().getCapacity()) {
            List<Applicant> accepted = applicants.subList(0, faculty.getInfo().getCapacity());
            accepted.forEach(x -> emailService.sendCongratulationEmail(faculty, x.getPerson().getEmail()));
            applicants.removeAll(accepted);
            applicants.forEach(x -> emailService.sendMaybeNextTimeEmail(faculty, x.getPerson().getEmail()));
        } else  {
            applicants.forEach(x->emailService.sendCongratulationEmail(faculty,x.getPerson().getEmail()));
        }
        FacultyInfo facultyInfo = facultyInfoRepository.findByFaculty_Id(faculty.getId());
        facultyInfo.setAvailable(false);
    }
}
