package com.example.paging.service;

import com.example.paging.domain.Board;
import com.example.paging.dto.CursorPageResponse;
import com.example.paging.repository.BoardCursorRepository;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class CursorBoardServiceTest {

	@Test
	void trimsExtraRowAndBuildsNextCursor() {
		BoardCursorRepository repository = mock(BoardCursorRepository.class);
		CursorBoardService service = new CursorBoardService(repository);

		when(repository.findFirstPage(eq(4))).thenReturn(List.of(
				new Board(10L, "10", "10"),
				new Board(9L, "9", "9"),
				new Board(8L, "8", "8"),
				new Board(7L, "7", "7")
		));

		CursorPageResponse response = service.getBoards(null, 3);

		assertThat(response.boards()).extracting(Board::getId).containsExactly(10L, 9L, 8L);
		assertThat(response.hasNext()).isTrue();
		assertThat(response.nextCursor()).isEqualTo(8L);
	}

	@Test
	void returnsSinglePageWithoutNextWhenDataIsShort() {
		BoardCursorRepository repository = mock(BoardCursorRepository.class);
		CursorBoardService service = new CursorBoardService(repository);

		when(repository.findNextPage(eq(5L), eq(4))).thenReturn(List.of(
				new Board(4L, "4", "4"),
				new Board(3L, "3", "3")
		));

		CursorPageResponse response = service.getBoards(5L, 3);

		assertThat(response.boards()).extracting(Board::getId).containsExactly(4L, 3L);
		assertThat(response.hasNext()).isFalse();
		assertThat(response.nextCursor()).isEqualTo(3L);
	}
}
