package com.example.paging.init;

import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
public class SampleBoardInitializer implements ApplicationRunner {

	private final JdbcTemplate jdbcTemplate;

	public SampleBoardInitializer(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	@Override
	public void run(ApplicationArguments args) {
		Integer count = jdbcTemplate.queryForObject("SELECT COUNT(*) FROM board", Integer.class);
		if (count != null && count > 0) {
			return;
		}

		for (int index = 1; index <= 95; index++) {
			jdbcTemplate.update(
					"INSERT INTO board(title, content) VALUES (?, ?)",
					"샘플 게시글 " + index,
					index + "번째 게시글 내용입니다."
			);
		}
	}
}
