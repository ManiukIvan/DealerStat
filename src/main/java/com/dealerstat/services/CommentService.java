package com.dealerstat.services;

import com.dealerstat.entities.Comment;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public interface CommentService {
    List<Comment> getAllComments();

    List<Comment> getAllApprovedComments();

    List<Comment> getAllUnApprovedComments();

    List<Comment> getAllApprovedCommentsByUserId(long userId);

    List<Comment> getAllUnApprovedCommentsByUserId(long userId);

    Optional<Comment> getCommentById(Long id);

    void addComment(Comment comment);

    void approveCommentById(Long id);

    void removeCommentById(Long id);
}