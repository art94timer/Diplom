package com.art.dip.controller;

import com.art.dip.model.VerifyToken;
import com.art.dip.service.interfaces.AuthService;
import com.art.dip.utility.dto.PersonDTO;
import com.art.dip.utility.exception.PersonAlreadyExistException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;

@Controller("/")
public class AuthController {



    private final AuthService service;


    @Autowired
    public AuthController(AuthService service) {
        this.service = service;
    }

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @GetMapping("/register")
    public String register(@ModelAttribute PersonDTO personDTO,Model model) {
        model.addAttribute("personDTO",personDTO);
        return "registerForm";
    }

    @GetMapping("/registrationConfirm")
    public String confirmRegistration(@RequestParam("token") String token, RedirectAttributes attributes) {
        VerifyToken verificationToken = service.getVerifyToken(token);
        if (verificationToken == null) {
            attributes.addFlashAttribute("message",service.tokenIsExpiredMessage());
            return "redirect:/register";
        }
        service.verifyPerson(verificationToken);
        return "redirect:/login";
    }

    @PostMapping("/save")
    public String save(@ModelAttribute @Valid PersonDTO personDTO, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("personDTO",personDTO);
            return "registerForm";
        }
        service.registerNewPersonAccount(personDTO);
        model.addAttribute("message",service.getVerifyYourEmailMessage());
        return "infoMessage";
    }

    @ExceptionHandler(PersonAlreadyExistException.class)
    public String handlePersonAlreadyExist(PersonAlreadyExistException ex,Model model) {
        model.addAttribute("message",ex.getMessage()).
                addAttribute("personDTO",ex.getPerson());
        return "registerForm";
    }
}
