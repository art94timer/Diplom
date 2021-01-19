package com.art.dip.utility.handler;

import com.art.dip.utility.exception.ValidationFormException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

@ControllerAdvice
public class ExcHandler {


    @ExceptionHandler(ValidationFormException.class)
    public ModelAndView handleValidationError(ValidationFormException ex) {
        return new ModelAndView("errorValidateForm","message",ex.getMessage());
    }


}
