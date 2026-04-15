package com.example.paging.dto;

import java.util.List;

public record PureJavaPagingView(
		PagingInfo pagingInfo,
		List<String> pageData
) {
}
