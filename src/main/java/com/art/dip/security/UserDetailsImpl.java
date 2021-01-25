package com.art.dip.security;

import com.art.dip.model.Person;
import com.art.dip.service.AuthServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Collections;


@Service
public class UserDetailsImpl implements UserDetailsService {

	@Autowired
	private AuthServiceImpl service;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {



		Person person = service.findByEmail(username).get();

		return new SecurityUser(person.getId(),person.getLocale(),
				person.getEmail(), 
				person.getCredential().getPassword(),
				person.isEnabled(),
				true,true,true,
				grant(person));
	}

	private Collection<? extends SimpleGrantedAuthority> grant(Person person) {
		return Collections.singleton(new SimpleGrantedAuthority("ROLE_" + person.getRole()));
	}
	

}