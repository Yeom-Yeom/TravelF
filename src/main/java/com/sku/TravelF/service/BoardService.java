package com.sku.TravelF.service;

import com.sku.TravelF.domain.Board;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface BoardService {
    public Page<Board> findBoardList(String search, Pageable pageable);
    public Page<Board> findBoardType(String boardType, String search, Pageable pageable);
    public List<Board> findTop5(String boardType);
    public Board findBoardById(Long id);
    public Board save(Board board);
    public void deleteById(Long id);

}
