package com.art.dip.controller;

import com.art.dip.model.Person;
import com.art.dip.service.ViewServiceImpl;
import com.art.dip.service.interfaces.ViewService;
import com.art.dip.utility.dto.AccountInfoDTO;
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
	
	private final ViewService service;

	@Autowired
	public FacultyInfoController(ViewService service) {
		this.service = service;
	}


	@GetMapping
	public String selectFaculty(Model model) {
		model.addAttribute("faculties",service.getAllFaculties());
		return "view";
	}


	@PostMapping("/faculty")
	public String notifyMe(@RequestParam Integer facultyId,Model model) {
		service.notifyMe(facultyId);
		model.addAttribute("message",service.getWeSendYouEmailMessage());
		return "infoMessage";
	}

	@GetMapping("/account")
	public String account(Model model) {
		AccountInfoDTO accountInfo = service.getAccountInfo();
		if (accountInfo == null) {
			return "redirect:/";
		}
		model.addAttribute("account",accountInfo);
		return "accountInfo";
	}

	@GetMapping("/faculty/{id}")
	public String getFacultyInfo(@PathVariable("id") Integer facultyId, Model model) {
		model.addAttribute("faculty",service.getFacultyInfo(facultyId));
		return "facultyInfo";

	}
}
