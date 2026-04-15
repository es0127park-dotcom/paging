package com.example.paging.service;

import com.example.paging.dto.PagingInfo;
import com.example.paging.dto.PureJavaPagingView;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class PureJavaPagingService {

	private static final int TOTAL_POSTS = 95;

	public PureJavaPagingView buildExample(int currentPage, int pageSize, int blockSize) {
		List<String> posts = createPosts();
		PagingInfo pagingInfo = new PagingInfo(currentPage, pageSize, posts.size(), blockSize);
		List<String> pageData = getPage(posts, pagingInfo);
		return new PureJavaPagingView(pagingInfo, pageData);
	}

	private List<String> createPosts() {
		List<String> posts = new ArrayList<>();
		for (int index = 1; index <= TOTAL_POSTS; index++) {
			posts.add("게시글 " + index);
		}
		return posts;
	}

	private List<String> getPage(List<String> items, PagingInfo pagingInfo) {
		if (pagingInfo.getStartIndex() >= items.size()) {
			return List.of();
		}
		return items.subList(pagingInfo.getStartIndex(), pagingInfo.getEndIndex());
	}
}
