package com.art.dip.controller;

import com.art.dip.model.Faculty;
import com.art.dip.model.FacultyInfo;
import com.art.dip.model.Subject;
import com.art.dip.service.interfaces.AdminCRUDService;
import com.art.dip.utility.exception.FacultyCRUDException;
import com.art.dip.utility.exception.SubjectCRUDException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/admin")
public class AdminCRUDController {


    private final AdminCRUDService service;

    @Autowired
    public AdminCRUDController(AdminCRUDService service) {
        this.service = service;
    }

    @GetMapping("/subjects")
    public String allSubjects(Model model) {
        model.addAttribute("subjects",service.getSubjects());
        return "subjects";
    }


    @GetMapping("/subject")
    public String addSubjectForm(Model model, @ModelAttribute Subject subject, @ModelAttribute Faculty faculty) {
        model.addAttribute("subject",subject).addAttribute("faculty",faculty);
        return "subjectForm";
    }

    @GetMapping("/subject/{id}")
    public String editSubject(@PathVariable("id") Integer subjectId, Model model) {
        model.addAttribute("subject",service.getSubjectWithFaculties(subjectId));
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
    public String addSubject(@ModelAttribute @Valid Subject subject, BindingResult bindingResult, Model model) {
        if(bindingResult.hasErrors()) {
            model.addAttribute("subject",subject);
            return "subjectForm";
        }
        service.addSubject(subject);
        return "redirect:/admin/subjects";
    }

    @GetMapping("/faculties")
    public String faculties(Model model) {
        List<Faculty> facultiesWithInfo = service.getFacultiesWithInfo();
        List <Faculty> available = new ArrayList<>();
        List <Faculty> notAvailable = new ArrayList<>();
        facultiesWithInfo.forEach(x->{
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
            Faculty f = service.createFaculty(faculty);
            return "redirect:/admin/faculties".concat(String.valueOf(f.getId()));
        }catch (FacultyCRUDException ex) {
            model.addAttribute("message",ex.getMessage());
            model.addAttribute("faculty",faculty);
            return "facultyForm";
        }
    }

    @GetMapping("/facultyInfo/{id}")
    public String facultyForm(@PathVariable(value = "id") Integer facultyId,Model model) {
        FacultyInfo faculty = service.getFacultyInfo(facultyId);
        model.addAttribute("faculty",faculty);
        model.addAttribute("facultyId",facultyId);
        return "facultyInfoForm";
    }

    @PostMapping("/facultyInfo")
    public String saveFaculty(@ModelAttribute FacultyInfo faculty, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("faculty",faculty);
            return "facultyInfoForm";
        }
        if (!faculty.isAvailable()) {
            service.openRecruiting(faculty);
        }
        else {
            service.updateAvailableFaculty(faculty);
            model.addAttribute("message",service.getSuccessfullyEditMessage());
        }
        return "redirect:/admin/faculties";
    }

    @GetMapping("/facultyInfo/disable/{id}")
    public String disableFaculty(@PathVariable("id") Integer facultyInfo) {
        service.disableFaculty(facultyInfo);
        return "redirect:/admin/faculties";
    }

    @GetMapping("/faculty/{id}")
    public String editFacultyForm(@PathVariable(value = "id") Integer facultyId,Model model) {
        Faculty faculty = service.getFacultyWithSubjects(facultyId);
        model.addAttribute("faculty",faculty);
        model.addAttribute("subjects",service.getSubjects());
        return "facultyForm";
    }

    @GetMapping("/faculty")
    public String createFacultyForm(@ModelAttribute Faculty faculty,Model model) {
        model.addAttribute("faculty",faculty);
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
        return "redirect:/admin/faculty/" + facultyId;
    }

    @PostMapping("/faculty/delete")
    public String deleteFaculty(Integer facultyId) {
        service.deleteFaculty(facultyId);
        return "redirect:/admin/faculties";
    }
}
