package service;

import com.art.dip.service.interfaces.AdminActionService;
import com.art.dip.utility.dto.ListValidateFormApplicantDTO;
import com.art.dip.utility.dto.ValidateFormApplicantDTO;
import com.art.dip.utility.exception.AdminMistakeApplicantFormException;
import config.TestConfig;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.ArrayList;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = TestConfig.class)
@TestPropertySource(locations="classpath:test.properties")
@SpringBootTest
public class AdminActionServiceTest {



    private final AdminActionService adminActionService;

    @Autowired
    public AdminActionServiceTest(AdminActionService adminActionService) {
        this.adminActionService = adminActionService;
    }


    @Test
    public void start() {
        Assertions.assertNotNull(adminActionService);
    }

    @Test
    public void checkForAdminMistakes() {
        ListValidateFormApplicantDTO listValidateFormApplicantDTO = new ListValidateFormApplicantDTO();
        listValidateFormApplicantDTO.setList(new ArrayList<>());
        ValidateFormApplicantDTO validateApplicantDTO = new ValidateFormApplicantDTO();
        validateApplicantDTO.setValid(false);
        validateApplicantDTO.setCauses(null);
        listValidateFormApplicantDTO.getList().add(validateApplicantDTO);
        AdminMistakeApplicantFormException adminMistakeApplicantFormException = Assertions.assertThrows(AdminMistakeApplicantFormException.class, () -> adminActionService.handleListForms(listValidateFormApplicantDTO));
        Assertions.assertNotNull(adminMistakeApplicantFormException);
        Assertions.assertNotNull(adminMistakeApplicantFormException.getMistakes());
        Assertions.assertFalse(adminMistakeApplicantFormException.getMistakes().isEmpty());
        Assertions.assertEquals(adminMistakeApplicantFormException.getMistakes().get(0),validateApplicantDTO);
    }



}
