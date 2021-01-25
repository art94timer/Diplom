package com.art.dip.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableWebSecurity
public class SpringSecConfiguration extends WebSecurityConfigurerAdapter {


    @Bean
    public PasswordEncoder encoder() {
        return NoOpPasswordEncoder.getInstance();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http.authorizeRequests().
                antMatchers("/admin", "/admin/**").hasAnyAuthority("ROLE_ADMIN")
                .and().
                authorizeRequests().antMatchers(HttpMethod.POST, "/admin/applicant").hasAuthority("ROLE_ADMIN")
                .and().
                authorizeRequests().antMatchers("/applicant", "/applicant/**").hasAnyAuthority("ROLE_ADMIN", "ROLE_USER")
                .and().
                authorizeRequests().antMatchers(HttpMethod.POST, "/applicant/**","/view/faculty").hasAnyAuthority("ROLE_ADMIN", "ROLE_USER")
                .antMatchers("/", "/register", "/login", "/registrationConfirm**").permitAll()
				.and().
                authorizeRequests().antMatchers(HttpMethod.POST, "/login", "/save", "/logout","/changeLang").permitAll()
				.and().
                logout().logoutSuccessUrl("/")
				.and().
                formLogin().loginPage("/login").defaultSuccessUrl("/")
                .failureUrl("/login?error=true")
                .and().
				logout().permitAll();

    }

}
