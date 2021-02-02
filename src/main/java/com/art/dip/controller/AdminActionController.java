package com.art.dip.controller;

import com.art.dip.service.interfaces.AdminActionService;
import com.art.dip.utility.dto.AdminSettings;
import com.art.dip.utility.dto.ListValidateFormApplicantDTO;
import com.art.dip.utility.dto.ValidateApplicantDTO;
import com.art.dip.utility.exception.AdminMistakeApplicantFormException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.List;


@Controller
@RequestMapping("/admin")
public class AdminActionController {

    public static final String ADMIN_SETTINGS = "Admin settings";

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
         List<ValidateApplicantDTO> applicants = service.prepareValidateFormList(settings);
        model.addAttribute("applicants",applicants);
        model.addAttribute("faculties",service.getFaculties());
        model.addAttribute("settings",settings);
        return "adminWork";
    }


    @PostMapping("/applicants")
    public String validateApplicant(@ModelAttribute @Valid ListValidateFormApplicantDTO listValidateFormApplicantDTO, BindingResult bindingResult,Model model, HttpSession session) {
        try {
            service.handleListForms(listValidateFormApplicantDTO);
        }catch (AdminMistakeApplicantFormException ex) {
            model.addAttribute("applicants", service.resolveMistakes(ex.getMistakes()));
            model.addAttribute("message",ex.getMessage());
            model.addAttribute("faculties",service.getFaculties());
            model.addAttribute("settings",session.getAttribute(ADMIN_SETTINGS));
            return "adminWork";
        }
        return "redirect:/admin/applicants";
    }


}
