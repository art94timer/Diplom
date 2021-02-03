package com.art.dip.controller;

import com.art.dip.service.interfaces.InfoService;
import com.art.dip.utility.dto.AccountInfoDTO;
import com.art.dip.utility.dto.FacultyInfoDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.sql.Time;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
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
	public String getFacultyInfo(@PathVariable("id") Integer facultyId, Model model, HttpServletRequest request, TimeZone timeZone) {
		FacultyInfoDTO facultyInfo = service.getFacultyInfo(facultyId);
		LocalDateTime updateTime = facultyInfo.getUpdateTime();
		ZonedDateTime zonedDateTime = updateTime.atZone(ZoneId.of("UTC"));
		LocalDateTime time = zonedDateTime.withZoneSameInstant(LocaleContextHolder.getTimeZone().toZoneId()).toLocalDateTime();
		facultyInfo.setUpdateTime(time);



		model.addAttribute("faculty",service.getFacultyInfo(facultyId));
		request.getCookies();

		model.addAttribute("zone","");
		return "facultyInfo";

	}
	@GetMapping("/message")
	public String infoMessage() {
		return "infoMessage";
	}
}
