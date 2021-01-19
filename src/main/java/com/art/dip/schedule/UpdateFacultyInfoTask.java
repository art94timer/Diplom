package com.art.dip.schedule;

import com.art.dip.model.Faculty;
import com.art.dip.model.FacultyInfo;
import com.art.dip.repository.FacultyInfoRepository;
import com.art.dip.repository.FacultyRepository;
import com.art.dip.utility.dto.UpdateFacultyDTO;
import org.decimal4j.util.DoubleRounder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Component
public class UpdateFacultyInfoTask {

    private final FacultyInfoRepository facInfoRepository;

    private final FacultyRepository facRepository;

    @Autowired
    public UpdateFacultyInfoTask(FacultyInfoRepository facInfoRepository, FacultyRepository facultyRepository) {
        this.facInfoRepository = facInfoRepository;
        this.facRepository = facultyRepository;
    }

    @Scheduled(fixedRate = 1000 * 60 * 60)
    @Transactional
    public void facultyUpdateInfo() {
        List<Faculty> faculties = facRepository.findAll();
        faculties.forEach(fac -> {
           FacultyInfo info = fac.getInfo();
                Optional<FacultyInfo> optFacInfo = facInfoRepository.findById(info.getId());
                if (optFacInfo.isPresent()) {
                    FacultyInfo facultyInfo = optFacInfo.get();
                    UpdateFacultyDTO updated = facInfoRepository.updateFacultyInfo(facultyInfo.getId());
                    if (updated.getAvg() == null)
                        updated.setAvg(0d);
                    facultyInfo.setAverageScore(DoubleRounder.round(updated.getAvg(), 1));
                    facultyInfo.setCountApplicants(updated.getCount().intValue());
                    facultyInfo.setUpdateTime(LocalDateTime.now());
                }
            });



    }

}
