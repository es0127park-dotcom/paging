package com.example.paging.repository;

import com.example.paging.domain.Board;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BoardJpaRepository extends JpaRepository<Board, Long> {

	Page<Board> findByTitleContaining(String keyword, Pageable pageable);
}
