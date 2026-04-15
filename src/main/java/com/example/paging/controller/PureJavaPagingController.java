package com.example.paging.controller;

import com.example.paging.dto.PureJavaPagingView;
import com.example.paging.service.PureJavaPagingService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class PureJavaPagingController {

	private final PureJavaPagingService pureJavaPagingService;

	public PureJavaPagingController(PureJavaPagingService pureJavaPagingService) {
		this.pureJavaPagingService = pureJavaPagingService;
	}

	@GetMapping("/steps/pure-java")
	public String pureJavaExample(
			@RequestParam(defaultValue = "3") int page,
			@RequestParam(defaultValue = "10") int size,
			Model model
	) {
		PureJavaPagingView view = pureJavaPagingService.buildExample(page, size, 3);
		model.addAttribute("pageData", view.pageData());
		model.addAttribute("paging", view.pagingInfo());
		return "steps/pure-java";
	}
}
