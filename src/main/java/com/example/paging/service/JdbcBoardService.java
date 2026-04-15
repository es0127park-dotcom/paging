package com.example.paging.service;

import com.example.paging.dto.BoardPageResponse;
import com.example.paging.dto.PagingInfo;
import com.example.paging.repository.JdbcBoardRepository;
import org.springframework.stereotype.Service;

@Service
public class JdbcBoardService {

	private final JdbcBoardRepository jdbcBoardRepository;

	public JdbcBoardService(JdbcBoardRepository jdbcBoardRepository) {
		this.jdbcBoardRepository = jdbcBoardRepository;
	}

	public BoardPageResponse getBoardPage(int page, int pageSize, int blockSize) {
		int totalCount = jdbcBoardRepository.count();
		PagingInfo pagingInfo = new PagingInfo(page, pageSize, totalCount, blockSize);
		return new BoardPageResponse(
				jdbcBoardRepository.findPage(pagingInfo.getOffset(), pagingInfo.getLimit()),
				pagingInfo
		);
	}
}
