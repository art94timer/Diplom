package com.art.dip.controller;

import com.art.dip.model.Person;
import com.art.dip.model.VerifyToken;
import com.art.dip.service.interfaces.PersonService;
import com.art.dip.utility.dto.PersonDTO;
import com.art.dip.utility.event.OnRegistrationCompleteEvent;
import com.art.dip.utility.exception.PersonAlreadyExistException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

@Controller
@RequestMapping("/")
public class AuthController {

    private final ApplicationEventPublisher eventPublisher;

    private final PersonService service;


    @Autowired
    public AuthController(ApplicationEventPublisher eventPublisher, PersonService service) {
        this.eventPublisher = eventPublisher;
        this.service = service;
    }

    @GetMapping("/login")
    public ModelAndView login(@RequestParam(required = false) Boolean error) {
        return new ModelAndView("login", "error", error);
    }

    @GetMapping("/register")
    public ModelAndView register() {
        return new ModelAndView("registerForm").addObject("personDTO", new PersonDTO());
    }

    @GetMapping("/registrationConfirm")
    public ModelAndView confirmRegistration(@RequestParam("token") String token) {

        VerifyToken verificationToken = service.getVerifyToken(token);
        if (verificationToken == null) {
            return new ModelAndView("redirect:/register").addObject("message",
                    "Verification Token is Expired");
        }
        Person user = verificationToken.getPerson();
        user.setEnabled(true);
        service.saveRegisteredPerson(user);
        return new ModelAndView("redirect:/login");
    }

    @ExceptionHandler(PersonAlreadyExistException.class)
    public ModelAndView handlePersonAlreadyExist(PersonAlreadyExistException ex) {
        return new ModelAndView("registerForm", "message", ex.getMessage()).
                addObject("person", ex.getPerson());
    }

    @PostMapping("/save")
    public String save(@ModelAttribute @Valid PersonDTO personDTO, BindingResult bindingResult,
                       HttpServletRequest request, Model model) {
        if (bindingResult.hasErrors()) {
            return "registerForm";
        }

        Person registered = service.registerNewPersonAccount(personDTO);
        eventPublisher
                .publishEvent(new OnRegistrationCompleteEvent(getAppUrl(request),
						request.getLocale(), registered));


        return "successRegister";
    }

    private String getAppUrl(HttpServletRequest request) {
        return "http://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath();
    }


}
