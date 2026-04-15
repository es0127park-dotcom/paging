package com.example.paging.dto;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class PagingInfoTest {

	@Test
	void calculatesOffsetLimitAndBlockNumbers() {
		PagingInfo pagingInfo = new PagingInfo(5, 10, 95, 3);

		assertThat(pagingInfo.getOffset()).isEqualTo(40);
		assertThat(pagingInfo.getLimit()).isEqualTo(10);
		assertThat(pagingInfo.getTotalPages()).isEqualTo(10);
		assertThat(pagingInfo.getStartPage()).isEqualTo(4);
		assertThat(pagingInfo.getEndPage()).isEqualTo(6);
		assertThat(pagingInfo.getPageNumbers()).containsExactly(4, 5, 6);
	}

	@Test
	void clampsInvalidPageNumbers() {
		PagingInfo pagingInfo = new PagingInfo(99, 10, 21, 3);

		assertThat(pagingInfo.getCurrentPage()).isEqualTo(3);
		assertThat(pagingInfo.getNextPage()).isEqualTo(3);
		assertThat(pagingInfo.isHasNext()).isFalse();
	}
}
