package com.blog.blogapi.controller;

import com.blog.blogapi.application.commentcreate.CreateCommentRequest;
import com.blog.blogapi.application.commentcreate.CreateCommentResult;
import com.blog.blogapi.application.commentlike.LikeCommentRequest;
import com.blog.blogapi.domain.model.Comment;
import com.blog.blogapi.service.CommentService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v1/comment")
@AllArgsConstructor
public class CommentController {
    private final CommentService commentService;

    @GetMapping()
    public List<Comment> getAll() {
        return commentService.getAllComments();
    }

    @PostMapping("/create")
    public CreateCommentResult create(@RequestBody CreateCommentRequest request){
        return commentService.create(request);
    }

    @DeleteMapping("/delete/{commentId}")
    public boolean delete(@PathVariable Long commentId){
        return commentService.delete(commentId);
    }

    @PutMapping("/like")
    public boolean like(@RequestBody LikeCommentRequest request) {
        return commentService.like(request);
    }
}
