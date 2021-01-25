package com.art.dip.config;

import com.art.dip.repository.PersonRepository;
import com.art.dip.security.SecurityUser;
import org.springframework.lang.Nullable;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.servlet.i18n.CookieLocaleResolver;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Locale;

@Component
public class MyCookieLocaleResolver extends CookieLocaleResolver {

    private final PersonRepository repository;

    public MyCookieLocaleResolver(PersonRepository repository) {
        this.repository = repository;
    }

   @Override
   @Transactional
    public void setLocale(HttpServletRequest request, @Nullable HttpServletResponse response, @Nullable Locale locale)  {
        super.setLocale(request,response,locale);
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (!(auth instanceof AnonymousAuthenticationToken)) {
            SecurityUser person = (SecurityUser) auth.getPrincipal();
            repository.changeLocale(locale,person.getId());
        }
    }
}
