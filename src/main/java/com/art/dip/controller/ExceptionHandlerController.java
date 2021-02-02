package com.art.dip.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.NoHandlerFoundException;


@ControllerAdvice
@Slf4j
public class ExceptionHandlerController {

    @ExceptionHandler(NoHandlerFoundException.class)
    public String handleAllExceptions(Model model, Throwable ex) {
        log.error(String.format("Exception is %s. Cause: %s",ex.getClass().getSimpleName(),ex.getCause()));
        model.addAttribute("message", ex.getMessage());
        return "infoMessage";
    }
}
