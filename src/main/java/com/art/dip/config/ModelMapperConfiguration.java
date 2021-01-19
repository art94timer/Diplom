package com.art.dip.config;

import org.modelmapper.AbstractConverter;
import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.modelmapper.config.Configuration.AccessLevel;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Configuration
public class ModelMapperConfiguration {

	@Bean
	@Scope(value="prototype")
	public ModelMapper modelMapper() {
		ModelMapper mapper = new ModelMapper();
		mapper.getConfiguration().setFieldAccessLevel(AccessLevel.PRIVATE);
		return mapper;
		
	}

	@Bean
	public Converter<String, LocalDate> toLocalDate() {
		return new AbstractConverter<>() {
			@Override
			protected LocalDate convert(String source) {
				DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyy-MM-dd");
				return LocalDate.parse(source, format);
			}
		};
	}
}
