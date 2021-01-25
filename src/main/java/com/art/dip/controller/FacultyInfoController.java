package com.art.dip.controller;

import com.art.dip.service.ViewServiceImpl;
import com.art.dip.utility.dto.FacultyInfoDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;


@Controller
@RequestMapping("/view")
public class FacultyInfoController {
	
	private final ViewServiceImpl service;

	@Autowired
	public FacultyInfoController(ViewServiceImpl service) {
		this.service = service;
	}


	@GetMapping
	public String selectFaculty(Model model) {

		model.addAttribute("faculties",service.getAllFaculties());
		return "facultyInfo";
	}


	@PostMapping("/faculty")
	public String notifyMe(@RequestParam Integer facultyId,Model model) {
		service.notifyMe(facultyId);
		model.addAttribute("message",service.getWeSendYouEmailMessage());
		return "redirect:/view";
	}

}
