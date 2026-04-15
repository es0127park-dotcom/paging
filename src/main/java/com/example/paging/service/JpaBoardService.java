package com.example.paging.service;

import com.example.paging.domain.Board;
import com.example.paging.repository.BoardJpaRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
public class JpaBoardService {

	private final BoardJpaRepository boardJpaRepository;

	public JpaBoardService(BoardJpaRepository boardJpaRepository) {
		this.boardJpaRepository = boardJpaRepository;
	}

	public Page<Board> getBoards(int page, int size) {
		return boardJpaRepository.findAll(createPageRequest(page, size));
	}

	public Page<Board> searchBoards(String keyword, int page, int size) {
		return boardJpaRepository.findByTitleContaining(keyword, createPageRequest(page, size));
	}

	private PageRequest createPageRequest(int page, int size) {
		PageRequest pageRequest = PageRequest.of(
				page - 1,
				size,
				Sort.by(Sort.Direction.DESC, "id")
		);
		return pageRequest;
	}
}
