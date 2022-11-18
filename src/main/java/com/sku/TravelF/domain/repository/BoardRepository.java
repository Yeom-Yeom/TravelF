package com.sku.TravelF.domain.repository;

import com.sku.TravelF.domain.Board;
import com.sku.TravelF.domain.User;
import com.sku.TravelF.domain.enums.BoardType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BoardRepository extends JpaRepository<Board, Long> {
    Board findByUser(User user);
    Page<Board> findBoardByBoardTypeOrderByModifiedDateDesc(BoardType boardType, Pageable pageable);
    Page<Board> findAllByOrderByModifiedDateDesc(Pageable pageable);
    List<Board> findTop5ByBoardTypeOrderByModifiedDateDesc(BoardType boardType);
}
