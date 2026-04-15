package com.example.paging.controller;

import com.example.paging.domain.Board;
import com.example.paging.service.JpaBoardService;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class JpaBoardController {

	private final JpaBoardService jpaBoardService;

	public JpaBoardController(JpaBoardService jpaBoardService) {
		this.jpaBoardService = jpaBoardService;
	}

	@GetMapping("/jpa/boards")
	public String boards(@RequestParam(defaultValue = "1") int page, Model model) {
		int safePage = Math.max(page, 1);
		Page<Board> boardPage = jpaBoardService.getBoards(safePage, 10);

		model.addAttribute("boards", boardPage.getContent());
		model.addAttribute("currentPage", safePage);
		model.addAttribute("totalPages", Math.max(boardPage.getTotalPages(), 1));
		return "jpa/boards";
	}
}
