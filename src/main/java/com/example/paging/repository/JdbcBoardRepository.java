package com.example.paging.repository;

import com.example.paging.domain.Board;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class JdbcBoardRepository {

	private final JdbcTemplate jdbcTemplate;

	public JdbcBoardRepository(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	public List<Board> findPage(int offset, int limit) {
		String sql = """
				SELECT id, title, content
				FROM board
				ORDER BY id DESC
				LIMIT ? OFFSET ?
				""";

		return jdbcTemplate.query(
				sql,
				(rs, rowNum) -> new Board(
						rs.getLong("id"),
						rs.getString("title"),
						rs.getString("content")
				),
				limit,
				offset
		);
	}

	public int count() {
		Integer count = jdbcTemplate.queryForObject("SELECT COUNT(*) FROM board", Integer.class);
		return count == null ? 0 : count;
	}
}
