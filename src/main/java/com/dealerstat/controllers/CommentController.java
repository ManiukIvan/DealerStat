package com.dealerstat.controllers;

import com.dealerstat.entities.Comment;
import com.dealerstat.exception.ApiException;
import com.dealerstat.exception.ApiRequestException;
import com.dealerstat.services.CommentService;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RequestMapping("/api")
@Controller
public class CommentController {
    private final CommentService commentService;

    public CommentController(@Qualifier("commentServiceImpl") CommentService commentService) {
        this.commentService = commentService;
    }

    @PostMapping("/comment/add")
    public ResponseEntity<HttpStatus> addComment(@RequestBody @Valid Comment comment, BindingResult bindingResult) {
        commentService.addComment(comment);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/comment/all")
    public ResponseEntity<List<Comment>> getAllComments() {
        return new ResponseEntity<>(commentService.getAllComments(), HttpStatus.OK);
    }

    @GetMapping("/comment/all/approved")
    public ResponseEntity<List<Comment>> getAllApprovedComments() {
        return new ResponseEntity<>(commentService.getAllApprovedComments(), HttpStatus.OK);
    }

    @GetMapping("/comment/all/unapproved")
    public ResponseEntity<List<Comment>> getAllUnapprovedComments() {
        return new ResponseEntity<>(commentService.getAllUnApprovedComments(), HttpStatus.OK);
    }

    @GetMapping("/user/{userId}/comment/all/approved")
    public ResponseEntity<List<Comment>> getAllApprovedCommentsByUserId(@PathVariable long userId) {
        return new ResponseEntity<>(commentService.getAllApprovedCommentsByUserId(userId), HttpStatus.OK);
    }

    @GetMapping("/comment/{id}")
    public ResponseEntity<Comment> getCommentById(@PathVariable Long id) {
        Comment comment = commentService.getCommentById(id).orElseThrow(() ->
                new ApiRequestException(new ApiException("There is no comment with id:" + id, HttpStatus.BAD_REQUEST)));
        return new ResponseEntity<>(comment, HttpStatus.OK);
    }

    @PostMapping("comment/{id}/approve")
    public ResponseEntity<HttpStatus> approveComment(@PathVariable long id) {
        commentService.approveCommentById(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("comment/{id}/denial-of-approve")
    public ResponseEntity<HttpStatus> removeComment(@PathVariable long id) {
        commentService.removeCommentById(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}