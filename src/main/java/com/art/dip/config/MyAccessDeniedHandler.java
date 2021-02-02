package com.art.dip.config;

import com.art.dip.utility.localization.MessageSourceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;
import java.util.Locale;

@Component
public class MyAccessDeniedHandler implements AccessDeniedHandler {

    private final MessageSourceService mesService;


    @Autowired
    public MyAccessDeniedHandler(MessageSourceService mesService) {
        this.mesService = mesService;
    }

    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) throws IOException, ServletException {
        Locale locale;
        response.setStatus(403);
        if (request.getCookies() == null) {
            request.setAttribute("message",mesService.getYouWillNotPass(Locale.ENGLISH));
            request.getRequestDispatcher("/view/message").forward(request,response);
            return;
        }
        Cookie l =  Arrays.stream(request.getCookies()).filter(x->x.getName().equals("locale")).findFirst().orElse(null);
        locale = l == null || l.getValue() == null ? Locale.ENGLISH : new Locale(l.getValue());
        request.setAttribute("message",mesService.getYouWillNotPass(locale));
        request.getRequestDispatcher("/view/message").forward(request,response);
    }
}
