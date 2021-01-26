package com.art.dip.controller;

import com.art.dip.model.Faculty;
import com.art.dip.model.FacultyInfo;
import com.art.dip.model.Subject;
import com.art.dip.service.interfaces.AdminActionService;
import com.art.dip.utility.dto.AdminSettings;
import com.art.dip.utility.dto.ListValidateFormApplicantDTO;
import com.art.dip.utility.dto.ValidateApplicantDTO;
import com.art.dip.utility.exception.AdminMistakeApplicantFormException;
import com.art.dip.utility.exception.FacultyCRUDException;
import com.art.dip.utility.exception.SubjectCRUDException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import java.util.ArrayList;
import java.util.List;

import static com.art.dip.utility.Constants.*;

@Controller
@RequestMapping("/admin")
public class AdminActionController {

    private final AdminActionService service;

    @Autowired
    public AdminActionController(AdminActionService service) {
        this.service = service;
    }


    @GetMapping("/menu")
    public String adminMenu() {
        return "adminMenu";
    }

    @GetMapping("/prepare")
    public String prepare(@ModelAttribute AdminSettings settings,Model model) {

        model.addAttribute("faculties",service.getFaculties()).
                addAttribute("settings",settings);

        return "prepareAdminWork";

    }

    @PostMapping("/prepare")
    public String start(AdminSettings settings, HttpSession session) {

        session.setAttribute(ADMIN_SETTINGS,settings);

        return "redirect:/admin/applicants";
    }


    @GetMapping("/applicants")
    public String listApplicants(@ModelAttribute ListValidateFormApplicantDTO list, Model model, HttpSession session) {
        AdminSettings settings = (AdminSettings)session.getAttribute(ADMIN_SETTINGS);
        if (settings == null) {
            return "redirect:/admin/prepare";
        }
         List<ValidateApplicantDTO> applicants = service.prepareValidateFormModelAndView(settings);
        model.addAttribute("applicants",applicants);
        model.addAttribute("faculties",service.getFaculties());
        model.addAttribute("settings",settings);
        return "adminWork";
    }


    @PostMapping("/applicants")
    public String validateApplicant(@ModelAttribute @Valid ListValidateFormApplicantDTO ListValidateFormApplicantDTO, BindingResult bindingResult,Model model) {
        try {
            service.handleListForms(ListValidateFormApplicantDTO);
        }catch (AdminMistakeApplicantFormException ex) {
            model.addAttribute("applicants", service.resolveMistakes(ex.getMistakes()));
            model.addAttribute("message",ex.getMessage());
            model.addAttribute("faculties",service.getFaculties());
            return "adminWork";
        }
        return "redirect:/admin/applicants";
    }



    @GetMapping("/subjects")
    public String allSubjects(Model model) {
        model.addAttribute("subjects",service.getSubjects());
        return "subjects";
    }


    @GetMapping("/subject")
    public String addSubjectForm(Model model,@ModelAttribute Subject subject, @ModelAttribute Faculty faculty) {
        model.addAttribute("faculties",service.getFaculties()).
                addAttribute("subject",subject).addAttribute("faculty",faculty);
        return "subjectForm";
    }

    @GetMapping("/subject/{id}")
    public String editSubject(@PathVariable("id") Integer subjectId, Model model) {
        model.addAttribute("subject",service.getSubjectWithFaculties(subjectId));
        model.addAttribute("allFaculties",service.getAllFacultiesIsNotAvailable());
        return "subjectForm";
    }


    @PostMapping("/subject/delete")
    public String deleteSubject(@RequestParam Integer subId, RedirectAttributes attributes) {
        try {
            service.deleteSubject(subId);
            attributes.addFlashAttribute("message",service.getSuccessfullyDeleteSubjectMessage());
        }catch (SubjectCRUDException ex) {
            attributes.addFlashAttribute("message",ex.getMessage());
        }
        return "redirect:/admin/subjects";
    }
    @PostMapping("/subject")
    public String addSubject(@ModelAttribute @Valid Subject subject,BindingResult bindingResult,Model model) {
        if(bindingResult.hasErrors()) {
            model.addAttribute("subject",subject);
            return "subjectForm";
        }
        service.addSubject(subject);
        return "redirect:/admin/subjects";
    }

    @PostMapping("/subject/delete/faculty")
    public String deleteFaculty(@RequestParam Integer facultyId,@RequestParam Integer subjectId,RedirectAttributes attributes) {
        try {
            service.deleteFacultyFromSubject(facultyId,subjectId);
            attributes.addFlashAttribute("message",service.getSuccessfullyEditMessage());
        }catch (FacultyCRUDException ex) {
            attributes.addFlashAttribute("message",ex.getMessage());
        }
    return "redirect:/admin/subject/".concat(String.valueOf(subjectId));
    }

    @PostMapping("/subject/add/faculty")
    public String addFaculty(@RequestParam Integer facultyId,@RequestParam Integer subjectId,RedirectAttributes attributes) {
        try {
            service.addFacultyToSubject(facultyId,subjectId);
            attributes.addFlashAttribute("message",service.getSuccessfullyEditMessage());
        }catch (SubjectCRUDException exception) {
            attributes.addFlashAttribute("message", exception.getMessage());
        }
        return "redirect:/admin/subject/".concat(String.valueOf(subjectId));
    }

    @GetMapping("/faculties")
    public String faculties(Model model) {
        List<Faculty> facultiesWithInfo = service.getFacultiesWithInfo();
        List <Faculty> available = new ArrayList<>();
        List <Faculty> notAvailable = new ArrayList<>();
        facultiesWithInfo.stream().forEach(x->{
            if (x.getInfo().isAvailable()) {
                available.add(x);
            }
            else {
                notAvailable.add(x);
            }
        });

        model.addAttribute("available",available);
        model.addAttribute("notAvailable",notAvailable);
        return "faculties";
    }

    @PostMapping("/faculty")
    public String faculty(@ModelAttribute Faculty faculty,BindingResult bindingResult,Model model,RedirectAttributes attributes) {
        if(bindingResult.hasErrors()) {
            model.addAttribute("faculty",faculty);
            return "facultyForm";
        }
        try {
            service.createFaculty(faculty);
        }catch (FacultyCRUDException ex) {
            model.addAttribute("message",ex.getMessage());
            return "facultyForm";
        }

        return "redirect:/admin/faculties";
    }

    @GetMapping("/facultyInfo/{id}")
    public String facultyForm(@PathVariable("id") Integer facultyId,Model model) {
        FacultyInfo faculty = service.getFacultyInfo(facultyId);
        model.addAttribute("faculty",faculty);
        return "facultyInfoForm";
    }

    @PostMapping("/facultyInfo")
    public String saveFaculty(@ModelAttribute FacultyInfo faculty, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("faculty",faculty);
            return "facultyInfoForm";
        }

            service.updateAvailableFaculty(faculty);
            model.addAttribute("message",service.getSuccessfullyEditMessage());

        return "redirect:/admin/faculties";
    }

    @GetMapping("/faculty/{id}")
    public String editFacultyForm(@PathVariable("id") Integer facultyId,Model model) {
        Faculty faculty = service.getFacultyWithSubjects(facultyId);
        model.addAttribute("faculty",faculty);
        model.addAttribute("subjects",service.getSubjects());
        return "facultyForm";
    }
    @PostMapping("/faculty/delete/subject")
    public String deleteSubjectFromFaculty(@RequestParam Integer subjectId,@RequestParam Integer facultyId) {
        service.deleteSubjectFromFaculty(subjectId,facultyId);
        return "redirect:/admin/faculty/" + facultyId;
    }

    @PostMapping("/faculty/add/subject")
    public String addSubjectToFaculty(@RequestParam Integer subjectId,@RequestParam Integer facultyId) {
        service.addSubjectToFaculty(subjectId,facultyId);
        return "redirect:/admin/faculty/"+facultyId;
    }
}
