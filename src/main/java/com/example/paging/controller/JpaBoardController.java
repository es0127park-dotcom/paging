package com.example.paging.controller;

import com.example.paging.domain.Board;
import com.example.paging.dto.PageNumber;
import com.example.paging.service.JpaBoardService;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;

@Controller
public class JpaBoardController {

	private static final int SIZE = 10;
	private static final int BLOCK_SIZE = 3;

	private final JpaBoardService jpaBoardService;

	public JpaBoardController(JpaBoardService jpaBoardService) {
		this.jpaBoardService = jpaBoardService;
	}

	@GetMapping("/jpa/boards")
	public String boards(@RequestParam(defaultValue = "1") int page, Model model) {
		int safePage = Math.max(page, 1);
		Page<Board> boardPage = jpaBoardService.getBoards(safePage, SIZE);
		if (safePage > 1 && boardPage.getTotalPages() > 0 && safePage > boardPage.getTotalPages()) {
			safePage = boardPage.getTotalPages();
			boardPage = jpaBoardService.getBoards(safePage, SIZE);
		}
		int currentPage = populatePagingModel(model, boardPage, safePage, "");
		model.addAttribute("currentPage", currentPage);
		model.addAttribute("path", "/jpa/boards");
		model.addAttribute("keyword", "");
		return "jpa/boards";
	}

	@GetMapping("/jpa/boards/search")
	public String searchBoards(
			@RequestParam(defaultValue = "") String keyword,
			@RequestParam(defaultValue = "1") int page,
			Model model
	) {
		int safePage = Math.max(page, 1);
		Page<Board> boardPage = jpaBoardService.searchBoards(keyword, safePage, SIZE);
		if (safePage > 1 && boardPage.getTotalPages() > 0 && safePage > boardPage.getTotalPages()) {
			safePage = boardPage.getTotalPages();
			boardPage = jpaBoardService.searchBoards(keyword, safePage, SIZE);
		}
		int currentPage = populatePagingModel(model, boardPage, safePage, keyword);
		model.addAttribute("currentPage", currentPage);
		model.addAttribute("path", "/jpa/boards/search");
		model.addAttribute("keyword", keyword);
		return "jpa/boards";
	}

	private int populatePagingModel(Model model, Page<Board> boardPage, int requestedPage, String keyword) {
		int totalPages = Math.max(boardPage.getTotalPages(), 1);
		int currentPage = Math.min(requestedPage, totalPages);
		int startPage = ((currentPage - 1) / BLOCK_SIZE) * BLOCK_SIZE + 1;
		int endPage = Math.min(startPage + BLOCK_SIZE - 1, totalPages);

		List<PageNumber> pageNumbers = new ArrayList<>();
		for (int pageNumber = startPage; pageNumber <= endPage; pageNumber++) {
			pageNumbers.add(new PageNumber(pageNumber, pageNumber == currentPage));
		}

		model.addAttribute("boards", boardPage.getContent());
		model.addAttribute("totalPages", totalPages);
		model.addAttribute("hasPrevious", currentPage > 1);
		model.addAttribute("hasNext", currentPage < totalPages);
		model.addAttribute("previousPage", Math.max(currentPage - 1, 1));
		model.addAttribute("nextPage", Math.min(currentPage + 1, totalPages));
		model.addAttribute("pageNumbers", pageNumbers);
		model.addAttribute("hasKeyword", !keyword.isBlank());
		return currentPage;
	}
}
