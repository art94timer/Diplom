package com.art.dip.config;

import com.art.dip.utility.LocaleChanger;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.i18n.CookieLocaleResolver;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Locale;

@Component
public class MyCookieLocaleResolver extends CookieLocaleResolver {
    /*
    Can't invoke transactional method from non-transactional
    https://stackoverflow.com/questions/30868829/is-it-possible-to-invoke-transactional-method-from-non-transactional-method-in-s
     */
    private final LocaleChanger changer;

    public MyCookieLocaleResolver(LocaleChanger changer) {
        this.changer = changer;
    }


    @Override
    public void setLocale(HttpServletRequest request, @Nullable HttpServletResponse response, @Nullable Locale locale) {
        super.setLocale(request, response, locale);
        changer.changeLocale(locale);
    }
}
