package com.art.dip.service;

import com.art.dip.security.SecurityUser;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.Locale;
@Service
public class CurrentPersonInfoService {


    public Integer getCurrentLoggedPersonId() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null) {
            SecurityUser secUser = (SecurityUser)auth.getPrincipal();
            return secUser.getId();
        }

        return 0;
    }

    public Locale getCurrentLoggedPersonLocale() {

        return LocaleContextHolder.getLocale();
    }


}
