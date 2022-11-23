package com.sku.TravelF.service;

import com.sku.TravelF.domain.Comment;
import com.sku.TravelF.repository.CommentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService{

    private final CommentRepository commentRepository;

    // 페이징 처리된 댓글 리스트 반환
    public ArrayList<Comment> findBCommentList(Long id) {
        return commentRepository.findAllByBoard_IdOrderByModifiedDateDesc(id);
    }

    // 페이징 처리된 댓글 리스트 반환
    public ArrayList<Comment> findJCommentList(Long id) {
        return commentRepository.findAllByJournal_IdOrderByModifiedDateDesc(id);
    }

    // 댓글 ID로 조회
    public Comment findCommentById(Long id) {
        Comment comment = commentRepository.findById (id).orElse (new Comment());
        return comment;
    }

    // 댓글 저장
    public Comment save(Comment comment){
        Comment savedComment = commentRepository.save (comment);
        return savedComment;
    }

    // 댓글 삭제
    public void deleteById(Long id){
        commentRepository.deleteById (id);
    }
}
