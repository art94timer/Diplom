package com.art.dip.controller;

import com.art.dip.service.ApplicantRegisterServiceImpl;
import com.art.dip.utility.dto.ApplicantDTO;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;

@Controller
@RequestMapping("/applicant")
public class ApplicantRegisterController {

    private final ApplicantRegisterServiceImpl appService;

    public ApplicantRegisterController(ApplicantRegisterServiceImpl appService) {
        this.appService = appService;
    }

    @GetMapping
    public String chooseFaculty(Model model) {
        model.addAttribute("faculties", appService.getFaculties());
        return "chooseFaculty";

    }

    @RequestMapping(value = "/faculty", method = {RequestMethod.GET, RequestMethod.POST})
    public String applicantForm(@RequestParam(required = false) Integer facId, Model model, @ModelAttribute @Valid ApplicantDTO applicantDTO, BindingResult bindingResult) {
        if (facId == null) {
            return "redirect:/applicant";
        }
        applicantDTO = appService.prepareApplicantDTO(applicantDTO);
        model.addAttribute("faculty", appService.getFacultyWithSubjectsById(facId)).
                addAttribute("applicantDTO", applicantDTO);

        return "applicantForm";

    }

    @PostMapping("/save")
    public String applicant(@ModelAttribute @Valid ApplicantDTO applicantDTO, BindingResult bindingResult, RedirectAttributes attributes) {
        if (bindingResult.hasErrors()) {
            attributes.addFlashAttribute("applicantDTO", applicantDTO);
            attributes.addFlashAttribute("facId", applicantDTO.getFaculty().getId());
            return "redirect:/applicant/faculty?facId=" + applicantDTO.getFaculty().getId();
            //	return "forward:/applicant/faculty?facId=" + applicantDTO.getDetails().getFacultyId();
        }
        appService.save(applicantDTO);
        return "successApplicant";
    }

}
