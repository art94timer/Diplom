package com.art.dip;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class HelloUniversity {

	public static void main(String[] args) {
		SpringApplication.run(HelloUniversity.class, args);

	}

}
