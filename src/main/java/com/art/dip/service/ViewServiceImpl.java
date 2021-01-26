package com.art.dip.service;

import com.art.dip.model.Faculty;
import com.art.dip.model.FacultyInfo;
import com.art.dip.model.NotifyHolder;
import com.art.dip.repository.FacultyInfoRepository;
import com.art.dip.repository.FacultyRepository;
import com.art.dip.repository.NotifyHolderRepository;
import com.art.dip.service.interfaces.ViewService;
import com.art.dip.utility.converter.FacultyConverter;
import com.art.dip.utility.converter.FacultyInfoConverter;
import com.art.dip.utility.dto.FacultyInfoDTO;
import com.art.dip.utility.localization.MessageSourceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;

@Service
public class ViewServiceImpl implements ViewService {

    private final FacultyRepository facultyRepository;

    private final CurrentPersonInfoService currentPersonInfoService;

    private final FacultyInfoRepository facultyInfoRepository;

    private final FacultyInfoConverter facultyInfoConverter;

    private final FacultyConverter facultyConverter;

    private final MessageSourceService mesService;

    private final NotifyHolderRepository notifyHolderRepository;

    @Autowired
    public ViewServiceImpl(FacultyRepository facultyRepository,
                           CurrentPersonInfoService currentPersonInfoService,
                           FacultyInfoRepository facultyInfoRepository, FacultyInfoConverter facultyInfoConverter, FacultyConverter facultyConverter, MessageSourceService mesService, NotifyHolderRepository notifyHolderRepository) {
        this.facultyRepository = facultyRepository;
        this.currentPersonInfoService = currentPersonInfoService;
        this.facultyInfoRepository = facultyInfoRepository;
        this.facultyInfoConverter = facultyInfoConverter;
        this.facultyConverter = facultyConverter;
        this.mesService = mesService;
        this.notifyHolderRepository = notifyHolderRepository;
    }


    public List<FacultyInfoDTO> getAllFaculties() {
        List<FacultyInfo> faculties = facultyInfoRepository.findAll();
        if (currentPersonInfoService.getCurrentLoggedPersonLocale().getLanguage().equals("ru")) {
            return facultyInfoConverter.toRuFacultyInfoDTO(faculties);
        } else {
            return facultyInfoConverter.toEnFacultyInfoDTO(faculties);
        }
    }

    public FacultyInfoDTO getFacultyInfo(Integer id) {
        FacultyInfo facultyInfo = facultyInfoRepository.findByFaculty_Id(id);
        if (currentPersonInfoService.getCurrentLoggedPersonLocale().getLanguage().equals("ru")) {
            return facultyInfoConverter.toRuFacultyInfoDTO(facultyInfo);
        } else {
            return facultyInfoConverter.toRuFacultyInfoDTO(facultyInfo);
        }
    }

    @Transactional
    public void notifyMe(Integer facultyId) {

       NotifyHolder notifyHolder = notifyHolderRepository.getNotifyHolderByFaculty_Id(facultyId).orElse(null);
        if (notifyHolder == null) {
            notifyHolder = new NotifyHolder();
            Faculty faculty = facultyRepository.findById(facultyId).get();
            notifyHolder.setFaculty(faculty);
        }
        List<String> emails = Collections.singletonList(currentPersonInfoService.getCurrentLoggedPersonEmail());
        notifyHolder.setEmails(emails);
        notifyHolderRepository.save(notifyHolder);
    }

    public String getWeSendYouEmailMessage() {
        return mesService.getWeSendYouEmailMessage();
    }
}
