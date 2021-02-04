package com.art.dip;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

import javax.annotation.PostConstruct;
import java.time.LocalDateTime;
import java.util.TimeZone;

@SpringBootApplication
@EnableScheduling
public class HelloUniversity {

	@PostConstruct
	public void init(){
		TimeZone.setDefault(TimeZone.getTimeZone("UTC"));   // It will set UTC timezone
		System.out.println("Spring boot application running in UTC timezone :"+ LocalDateTime.now());   // It will print UTC timezone
	}
	public static void main(String[] args) {
		SpringApplication.run(HelloUniversity.class, args);

	}

}
