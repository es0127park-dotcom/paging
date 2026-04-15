package com.example.paging.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class HomeController {

	@GetMapping("/")
	public String home(Model model) {
		model.addAttribute("steps", List.of(
				new StepLink("예제 (0)", "학습 순서", "순수 Java -> JdbcTemplate -> JPA -> 검색 유지 -> 커서 페이징 순서로 학습한다.", "/"),
				new StepLink("예제 (1)", "순수 Java 페이징", "startIndex, endIndex, totalPages 공식을 먼저 이해한다.", "/steps/pure-java"),
				new StepLink("예제 (2)", "JdbcTemplate 페이징", "LIMIT / OFFSET으로 DB 페이징을 직접 구현한다.", "/jdbc/boards"),
				new StepLink("예제 (3)", "JPA 페이징", "Pageable, Page, page - 1 규칙을 익힌다.", "/jpa/boards"),
				new StepLink("예제 (4)", "검색 조건 유지", "검색어를 유지한 채 페이지 블록을 계산한다.", "/jpa/boards/search"),
				new StepLink("예제 (5)", "커서 페이징", "마지막 id를 기준으로 다음 데이터를 가져온다.", "/cursor"),
				new StepLink("예제 (6)", "학습 마무리", "정렬, 마지막 페이지, size 제한 같은 방어 코드를 점검한다.", "/guide")
		));
		return "index";
	}

	@GetMapping("/guide")
	public String guide() {
		return "guide";
	}

	public record StepLink(String label, String title, String summary, String path) {
	}
}
