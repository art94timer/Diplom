package com.art.dip.controller;

import com.art.dip.service.ViewServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
@RequestMapping("/view")
public class ViewController {
	
	private final ViewServiceImpl service;

	@Autowired
	public ViewController(ViewServiceImpl service) {
		this.service = service;
	}


	@GetMapping
	public String selectFaculty(Model model) {
		model.addAttribute("faculties",service.getAllFaculties());
		return "faculties";
	}


	@GetMapping("/faculty/{id}")
	public String showFacultyInfo(@PathVariable Integer id,Model model) {
		model.addAttribute("faculty",service.getFacultyInfo(id));
		return "facultyInfo";
	}
	

	
//	@GetMapping("/faculty/{id}")
//	public String showDailyFacultyInfoTable(@PathVariable Integer id,Model model) {
//	model.addAttribute("faculty",service.getFacultyDTOWithDetailsById(id));
//	return "facultyResult";
//	}
	
}
