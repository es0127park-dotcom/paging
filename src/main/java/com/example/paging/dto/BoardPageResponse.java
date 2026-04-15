package com.example.paging.dto;

import com.example.paging.domain.Board;

import java.util.List;

public record BoardPageResponse(
		List<Board> boards,
		PagingInfo paging
) {
}
