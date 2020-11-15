package com.dealerstat.services.Impl;

import com.dealerstat.entities.Comment;
import com.dealerstat.exception.ApiException;
import com.dealerstat.exception.ApiRequestException;
import com.dealerstat.repositories.CommentRepository;
import com.dealerstat.services.CommentService;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CommentServiceImpl implements CommentService {
    private final CommentRepository commentRepository;

    public CommentServiceImpl(CommentRepository commentRepository) {
        this.commentRepository = commentRepository;
    }

    @Override
    public List<Comment> getAllComments() {
        return commentRepository.findAll();
    }

    @Override
    public List<Comment> getAllApprovedComments() {
        return commentRepository.findAllByApproved(true);
    }

    @Override
    public List<Comment> getAllUnApprovedComments() {
        return commentRepository.findAllByApproved(false);
    }

    @Override
    public List<Comment> getAllApprovedCommentsByUserId(long userId) {
        return commentRepository.findAllByUserIdAndApproved(userId, true);
    }

    @Override
    public List<Comment> getAllUnApprovedCommentsByUserId(long userId) {
        return commentRepository.findAllByUserIdAndApproved(userId, false);
    }

    @Override
    public Optional<Comment> getCommentById(Long id) {
        return commentRepository.findById(id);
    }

    @Override
    public void addComment(Comment comment) {
        commentRepository.save(comment);
    }

    @Override
    public void approveCommentById(Long id) {
        Comment comment = commentRepository.findById(id).orElseThrow(() ->
                new ApiRequestException(new ApiException("There is no comment with id:" + id, HttpStatus.BAD_REQUEST)));
        comment.setApproved(true);
        commentRepository.save(comment);

    }

    @Override
    public void removeCommentById(Long id) {
        commentRepository.deleteById(id);
    }
}
