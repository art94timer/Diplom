package com.art.dip.controller;

import com.art.dip.service.AdminActionServiceImpl;
import com.art.dip.utility.dto.AdminSettings;
import com.art.dip.utility.dto.ListValidateFormApplicantDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;

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
    public ModelAndView prepare(@ModelAttribute AdminSettings settings) {
        return new ModelAndView("adminWork",
                "faculties",service.getFaculties()).addObject("settings",settings);
    }

    @PostMapping("/prepare")
    public ModelAndView start(AdminSettings settings, HttpSession session) {
        session.setAttribute(ADMIN_SETTINGS,settings);

        return new ModelAndView("redirect:/admin/applicants");
    }


    @GetMapping("/applicants")
    public ModelAndView listApplicants(HttpSession session) {
        //redirect if admin session not contain admin settings
        return service.prepareValidateFormModelAndView(session);

    }


    @PostMapping("/applicants")
    public ModelAndView validateApplicant(@ModelAttribute ListValidateFormApplicantDTO list) {
        service.handleListForms(list);
        return new ModelAndView("index");
    }


}
