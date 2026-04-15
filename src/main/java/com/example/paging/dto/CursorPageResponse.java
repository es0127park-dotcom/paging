package com.example.paging.dto;

import com.example.paging.domain.Board;

import java.util.List;

public record CursorPageResponse(
		List<Board> boards,
		Long nextCursor,
		boolean hasNext
) {
}
