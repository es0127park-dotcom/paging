package com.example.paging.controller;

import com.example.paging.dto.CursorPageResponse;
import com.example.paging.service.CursorBoardService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class CursorPagingController {

	private final CursorBoardService cursorBoardService;

	public CursorPagingController(CursorBoardService cursorBoardService) {
		this.cursorBoardService = cursorBoardService;
	}

	@GetMapping("/cursor")
	public String cursorPage() {
		return "cursor/index";
	}

	@ResponseBody
	@GetMapping("/api/cursor/boards")
	public CursorPageResponse getBoardsByCursor(
			@RequestParam(required = false) Long cursor,
			@RequestParam(defaultValue = "10") int size
	) {
		int safeSize = Math.min(Math.max(size, 1), 100);
		return cursorBoardService.getBoards(cursor, safeSize);
	}
}
