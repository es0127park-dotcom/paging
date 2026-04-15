package com.example.paging.dto;

import java.util.ArrayList;
import java.util.List;

public class PagingInfo {

	private final int currentPage;
	private final int pageSize;
	private final int totalCount;
	private final int totalPages;
	private final int blockSize;
	private final int startPage;
	private final int endPage;
	private final boolean hasPrevious;
	private final boolean hasNext;
	private final int previousPage;
	private final int nextPage;
	private final List<Integer> pageNumbers = new ArrayList<>();

	public PagingInfo(int currentPage, int pageSize, int totalCount, int blockSize) {
		this.pageSize = pageSize;
		this.totalCount = Math.max(totalCount, 0);
		this.blockSize = Math.max(blockSize, 1);

		int calculatedPages = (int) Math.ceil((double) this.totalCount / pageSize);
		this.totalPages = Math.max(calculatedPages, 1);

		if (currentPage < 1) {
			this.currentPage = 1;
		} else if (currentPage > this.totalPages) {
			this.currentPage = this.totalPages;
		} else {
			this.currentPage = currentPage;
		}

		this.startPage = ((this.currentPage - 1) / this.blockSize) * this.blockSize + 1;
		this.endPage = Math.min(this.startPage + this.blockSize - 1, this.totalPages);
		this.hasPrevious = this.currentPage > 1;
		this.hasNext = this.currentPage < this.totalPages;
		this.previousPage = this.hasPrevious ? this.currentPage - 1 : 1;
		this.nextPage = this.hasNext ? this.currentPage + 1 : this.totalPages;

		for (int pageNumber = this.startPage; pageNumber <= this.endPage; pageNumber++) {
			this.pageNumbers.add(pageNumber);
		}
	}

	public int getOffset() {
		return (currentPage - 1) * pageSize;
	}

	public int getLimit() {
		return pageSize;
	}

	public int getStartIndex() {
		return getOffset();
	}

	public int getEndIndex() {
		return Math.min(getOffset() + pageSize, totalCount);
	}

	public int getCurrentPage() {
		return currentPage;
	}

	public int getPageSize() {
		return pageSize;
	}

	public int getTotalCount() {
		return totalCount;
	}

	public int getTotalPages() {
		return totalPages;
	}

	public int getBlockSize() {
		return blockSize;
	}

	public int getStartPage() {
		return startPage;
	}

	public int getEndPage() {
		return endPage;
	}

	public boolean isHasPrevious() {
		return hasPrevious;
	}

	public boolean isHasNext() {
		return hasNext;
	}

	public int getPreviousPage() {
		return previousPage;
	}

	public int getNextPage() {
		return nextPage;
	}

	public List<Integer> getPageNumbers() {
		return pageNumbers;
	}
}
