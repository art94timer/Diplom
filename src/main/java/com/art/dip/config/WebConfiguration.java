package com.art.dip.config;

import com.art.dip.utility.LocaleChanger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor;
import org.thymeleaf.extras.java8time.dialect.Java8TimeDialect;

import java.util.Locale;
@Configuration
public class WebConfiguration implements WebMvcConfigurer {

    @Bean
    public LocaleResolver localeResolver(@Autowired  LocaleChanger changer) {
        MyCookieLocaleResolver localeResolver = new MyCookieLocaleResolver(changer);
        localeResolver.setDefaultLocale(Locale.ENGLISH);
        localeResolver.setCookieName("locale");
        return localeResolver;
    }

    @Bean
    public LocaleChangeInterceptor localeChangeInterceptor() {
        LocaleChangeInterceptor localeChangeInterceptor = new LocaleChangeInterceptor();
        localeChangeInterceptor.setParamName("lang");
        return localeChangeInterceptor;
    }

    @Bean
    public Java8TimeDialect java8TimeDialect() {
        return new Java8TimeDialect();
    }


    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(localeChangeInterceptor());
    }
}
