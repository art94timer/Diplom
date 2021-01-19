package com.art.dip.utility.event;


import com.art.dip.model.Person;
import lombok.Data;
import org.springframework.context.ApplicationEvent;

import java.io.Serial;
import java.util.Locale;
@Data
public class OnRegistrationCompleteEvent extends ApplicationEvent {

	
    private final String appUrl;
    private final Locale locale;
    private final Person person;
    
	public OnRegistrationCompleteEvent(String appUrl, Locale locale, Person person) {
		super(person);
		this.appUrl = appUrl;
		this.locale = locale;
		this.person = person;
	}
    
	

}
