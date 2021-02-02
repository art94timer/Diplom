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
import java.util.List;
import java.util.Map;
import java.util.TimeZone;

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

    @RequestMapping(value = "/subject/{action}",method = {RequestMethod.GET,RequestMethod.POST})
    public String actionSubject(@PathVariable String action, @RequestParam(value = "id",required = false) Integer subjectId,
                                Model model,
                                RedirectAttributes attributes) {
        switch (action) {
            case "edit" : {
                Subject subject = service.getSubject(subjectId);
                if (subject != null) {
                    model.addAttribute("subject", subject);
                    return "subjectForm";
                }
            }
            case "delete": {
                try {
                    service.deleteSubject(subjectId);
                    attributes.addFlashAttribute(service.getSuccessfullyDeleteSubjectMessage());
                    break;
                }catch (SubjectCRUDException ex) {
                    attributes.addFlashAttribute("message",ex.getMessage());
                }
            }
        }
        return "redirect:/admin/subjects";
    }

    @PostMapping("/subject")
    public String createOrEditSubject(@ModelAttribute @Valid Subject subject, BindingResult bindingResult, Model model,RedirectAttributes attributes) {
        if(bindingResult.hasErrors()) {
            model.addAttribute("subject",subject);
            return "subjectForm";
        }
        try {
            service.createOrEditSubject(subject);
        }catch (SubjectCRUDException ex) {
            attributes.addFlashAttribute("message",ex.getMessage());
        }
        return "redirect:/admin/subjects";
    }

    @GetMapping("/faculties")
    public String faculties(Model model, TimeZone timeZone) {
        Map<String,List<Faculty>> map = service.getFacultiesWithInfo();
        model.addAttribute("available",map.get("available"));
        model.addAttribute("notAvailable",map.get("notAvailable"));
        model.addAttribute("zone",timeZone);
        return "faculties";
    }

    @GetMapping("/faculty")
    public String createFacultyForm(@ModelAttribute Faculty faculty,Model model) {
        model.addAttribute("faculty",faculty);
        return "facultyForm";
    }

    @PostMapping("/faculty")
    public String faculty(@ModelAttribute Faculty faculty,BindingResult bindingResult,Model model,RedirectAttributes attributes) {
        if(bindingResult.hasErrors()) {
            model.addAttribute("faculty",faculty);
            return "facultyForm";
        }
        try {
             service.createOrEditFaculty(faculty);
            return "redirect:/admin/faculties";
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
        try {
            if (!faculty.isAvailable()) {
                service.openRecruiting(faculty);
            } else {
                service.updateAvailableFaculty(faculty);
                model.addAttribute("message",service.getSuccessfullyEditMessage());
            }
        }catch (FacultyCRUDException ex) {
            model.addAttribute("message",ex.getMessage());
            model.addAttribute("faculty",faculty);
            return "facultyInfoForm";
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



    @PostMapping("/faculty/{action}/subject")
    public String deleteSubjectFromFaculty(@RequestParam Integer subjectId,@PathVariable String action,@RequestParam Integer facultyId) {
        switch (action) {
            case "delete": {
                service.deleteSubjectFromFaculty(subjectId, facultyId);
                break;
            }
            case "add" : {
                service.addSubjectToFaculty(subjectId, facultyId);
                break;
            }
        }
        return "redirect:/admin/faculty/" + facultyId;
    }

    @PostMapping("/faculty/delete")
    public String deleteFaculty(Integer facultyId) {
        service.deleteFaculty(facultyId);
        return "redirect:/admin/faculties";
    }
}
