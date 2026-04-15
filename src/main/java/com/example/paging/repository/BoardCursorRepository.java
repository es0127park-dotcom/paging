package com.example.paging.repository;

import com.example.paging.domain.Board;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class BoardCursorRepository {

	private final JdbcTemplate jdbcTemplate;

	public BoardCursorRepository(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	public List<Board> findFirstPage(int limit) {
		String sql = """
				SELECT id, title, content
				FROM board
				ORDER BY id DESC
				LIMIT ?
				""";
		return jdbcTemplate.query(sql, this::mapBoard, limit);
	}

	public List<Board> findNextPage(Long lastId, int limit) {
		String sql = """
				SELECT id, title, content
				FROM board
				WHERE id < ?
				ORDER BY id DESC
				LIMIT ?
				""";
		return jdbcTemplate.query(sql, this::mapBoard, lastId, limit);
	}

	private Board mapBoard(java.sql.ResultSet rs, int rowNum) throws java.sql.SQLException {
		return new Board(
				rs.getLong("id"),
				rs.getString("title"),
				rs.getString("content")
		);
	}
}
