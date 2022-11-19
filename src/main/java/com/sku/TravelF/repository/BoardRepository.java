package com.sku.TravelF.repository;

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

    Page<Board> findAllByOrderByModifiedDateDesc(Pageable pageable);
    Page<Board> findAllByTitleContainingOrderByModifiedDateDesc(String title, Pageable pageable);

    Page<Board> findBoardByBoardTypeOrderByModifiedDateDesc(BoardType boardType, Pageable pageable);
    Page<Board> findBoardByBoardTypeAndTitleContainingOrderByModifiedDateDesc(BoardType boardType, String title, Pageable pageable);

    Board findByUser(User user);
    List<Board> findTop5ByBoardTypeOrderByModifiedDateDesc(BoardType boardType);
}
