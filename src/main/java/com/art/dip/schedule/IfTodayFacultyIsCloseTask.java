package com.art.dip.schedule;

import com.art.dip.model.Faculty;
import com.art.dip.model.FacultyInfo;
import com.art.dip.repository.ApplicantRepository;
import com.art.dip.repository.FacultyInfoRepository;
import com.art.dip.service.interfaces.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

@Component
public class IfTodayFacultyIsCloseTask {

    private final ApplicantRepository applicantRepository;

    private final FacultyInfoRepository facultyInfoRepository;

    private final EmailService emailService;

    private final ScheduledExecutorService ses = Executors.newScheduledThreadPool(100);

    @Autowired
    public IfTodayFacultyIsCloseTask(ApplicantRepository applicantRepository, FacultyInfoRepository facultyRepository, EmailService emailService) {
        this.applicantRepository = applicantRepository;
        this.facultyInfoRepository = facultyRepository;
        this.emailService = emailService;
    }
    //Seconds Minutes Hours DayOfMonth Month DayOfWeek
    @Scheduled(cron = "0 0 0 * * ?")
    public void checkIfTodayFacultyIsClose() {
        List<FacultyInfo> allFaculties = facultyInfoRepository.findByIsAvailableTrue();
        LocalDate now = LocalDate.now();
        allFaculties.forEach(x-> {
            if(x.getExpiredDate().toLocalDate().equals(now)) {
               long timeInMinutes = x.getExpiredDate().toLocalTime().getHour() * 60 + x.getExpiredDate().toLocalTime().getMinute();
               ses.schedule(createTask(x.getFaculty()),timeInMinutes, TimeUnit.MINUTES);
            }

        });
    }

    private Runnable createTask(Faculty faculty) {
        return new ApplicantEmailSendRunnableTask(faculty,emailService,applicantRepository, facultyInfoRepository);
    }
}
