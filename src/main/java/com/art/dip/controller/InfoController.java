package com.art.dip.controller;

import com.art.dip.service.interfaces.InfoService;
import com.art.dip.utility.dto.AccountInfoDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.TimeZone;


@Controller
@RequestMapping("/view")
public class InfoController {
	
	private final InfoService service;

	@Autowired
	public InfoController(InfoService service) {
		this.service = service;
	}


	@GetMapping
	public String faculties(Model model) {
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
	public String getFacultyInfo(@PathVariable("id") Integer facultyId, Model model, TimeZone timeZone) {
		model.addAttribute("faculty",service.getFacultyInfo(facultyId));
		model.addAttribute("zone",timeZone);
		return "facultyInfo";

	}
	@GetMapping("/message")
	public String infoMessage() {
		return "infoMessage";
	}
}
