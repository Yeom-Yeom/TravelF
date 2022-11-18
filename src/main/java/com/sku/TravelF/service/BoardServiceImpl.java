package com.sku.TravelF.service;

import com.sku.TravelF.domain.Board;
import com.sku.TravelF.domain.enums.BoardType;
import com.sku.TravelF.domain.repository.BoardRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BoardServiceImpl implements BoardService {

    private final BoardRepository boardRepository;

    // 페이징 처리된 게시글 리스트 반환
    public Page<Board> findBoardList(Pageable pageable) {
        pageable = PageRequest.of(pageable.getPageNumber () <= 0 ? 0 : pageable.getPageNumber ()-1, pageable.getPageSize ());
        return boardRepository.findAllByOrderByModifiedDateDesc (pageable);
    }

    // 게시글 리스트 반환
    public Page<Board> findBoardType(String boardType, Pageable pageable) {
        BoardType Type;
        if(boardType.equals ("공지사항")){
            Type = BoardType.notice;
        }else if(boardType.equals ("자유게시판")){
            Type = BoardType.free;
        }else {
            Type = null;
        }
        pageable = PageRequest.of(pageable.getPageNumber () <= 0 ? 0 : pageable.getPageNumber ()-1, pageable.getPageSize ());
        return boardRepository.findBoardByBoardTypeOrderByModifiedDateDesc (Type, pageable);
    }

    // 탑5 조회
    public List<Board> findTop5(String boardType) {
        BoardType Type;
        if(boardType.equals ("공지사항")){
            Type = BoardType.notice;
        }else if(boardType.equals ("자유게시판")){
            Type = BoardType.free;
        }else {
            Type = null;
        }
        return boardRepository.findTop5ByBoardTypeOrderByModifiedDateDesc (Type);
    }

    // 게시글 ID로 조회
    public Board findBoardById(Long id) {
        Board board = boardRepository.findById (id).orElse (new Board());
        return board;
    }

    // 게시글 저장
    public Board save(Board board){
        Board savedBoard = boardRepository.save (board);
        return savedBoard;
    }

    // 게시글 삭제
    public void deleteById(Long id){
        boardRepository.deleteById (id);
    }

}
