package com.example.paging.service;

import com.example.paging.domain.Board;
import com.example.paging.dto.CursorPageResponse;
import com.example.paging.repository.BoardCursorRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CursorBoardService {

	private final BoardCursorRepository boardCursorRepository;

	public CursorBoardService(BoardCursorRepository boardCursorRepository) {
		this.boardCursorRepository = boardCursorRepository;
	}

	public CursorPageResponse getBoards(Long cursor, int size) {
		int queryLimit = size + 1;
		List<Board> fetchedBoards = cursor == null
				? boardCursorRepository.findFirstPage(queryLimit)
				: boardCursorRepository.findNextPage(cursor, queryLimit);

		boolean hasNext = fetchedBoards.size() > size;
		List<Board> boards = hasNext ? fetchedBoards.subList(0, size) : fetchedBoards;
		Long nextCursor = boards.isEmpty() ? null : boards.get(boards.size() - 1).getId();
		return new CursorPageResponse(boards, nextCursor, hasNext);
	}
}
