package com.art.dip.controller;

import com.art.dip.service.AdminActionServiceImpl;
import com.art.dip.utility.dto.AdminSettings;
import com.art.dip.utility.dto.ListValidateFormApplicantDTO;
import com.art.dip.utility.dto.ValidateApplicantDTO;
import com.art.dip.utility.dto.ValidateFormApplicantDTO;
import com.art.dip.utility.exception.AdminMistakeApplicantFormException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import java.util.List;

import static com.art.dip.utility.Constants.*;

@Controller
@RequestMapping("/admin")
public class AdminActionController {

    private final AdminActionServiceImpl service;

    @Autowired
    public AdminActionController(AdminActionServiceImpl service) {
        this.service = service;
    }

    @GetMapping("/prepare")
    public String prepare(@ModelAttribute AdminSettings settings,Model model) {

        model.addAttribute("faculties",service.getFaculties()).
                addAttribute("settings",settings);

        return "adminWork";

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
        return "requests";
    }


    @PostMapping("/applicants")
    public String validateApplicant(@ModelAttribute @Valid ListValidateFormApplicantDTO ListValidateFormApplicantDTO, BindingResult bindingResult,Model model) {
        try {
            service.handleListForms(ListValidateFormApplicantDTO);
        }catch (AdminMistakeApplicantFormException ex) {
            model.addAttribute("applicants", service.resolveMistakes(ex.getMistakes()));
            model.addAttribute("message",ex.getMessage());
            model.addAttribute("faculties",service.getFaculties());
            return "requests";
        }
        return "redirect:/admin/applicants";
    }

}