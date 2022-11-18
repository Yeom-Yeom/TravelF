package com.sku.TravelF.domain.repository;

import com.sku.TravelF.domain.Comment;
import com.sku.TravelF.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {
    Comment findByUser(User user);
    ArrayList<Comment> findAllByBoard_IdOrderByModifiedDateDesc(Long id);
    ArrayList<Comment> findAllByJournal_IdOrderByModifiedDateDesc(Long id);
}
