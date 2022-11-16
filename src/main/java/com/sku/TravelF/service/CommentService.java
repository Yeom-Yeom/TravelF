package com.sku.TravelF.service;

import com.sku.TravelF.domain.Comment;

import java.util.ArrayList;

public interface CommentService {
    public ArrayList<Comment> findBCommentList(Long id);
    public ArrayList<Comment> findJCommentList(Long id);
    public Comment findCommentById(Long id);
    public Comment save(Comment comment);
    public void deleteById(Long id);
}
