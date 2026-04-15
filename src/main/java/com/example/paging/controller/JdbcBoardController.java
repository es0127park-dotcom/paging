package com.example.paging.controller;

import com.example.paging.dto.BoardPageResponse;
import com.example.paging.service.JdbcBoardService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class JdbcBoardController {

	private final JdbcBoardService jdbcBoardService;

	public JdbcBoardController(JdbcBoardService jdbcBoardService) {
		this.jdbcBoardService = jdbcBoardService;
	}

	@GetMapping("/jdbc/boards")
	public String boards(@RequestParam(defaultValue = "1") int page, Model model) {
		BoardPageResponse response = jdbcBoardService.getBoardPage(page, 10, 3);
		model.addAttribute("boards", response.boards());
		model.addAttribute("paging", response.paging());
		return "jdbc/boards";
	}
}
