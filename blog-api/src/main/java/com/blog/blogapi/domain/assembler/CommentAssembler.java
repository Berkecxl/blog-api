package com.blog.blogapi.domain.assembler;

import com.blog.blogapi.application.commentcreate.CreateCommentRequest;
import com.blog.blogapi.domain.model.Comment;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class CommentAssembler {

    public Comment applyCommentWithCreateRequest(CreateCommentRequest request){
        Comment comment = new Comment();
        comment.setUsername(request.getUsername());
        comment.setMessage(request.getMessage());
        comment.setLikeCount(0);
        comment.setCreatedDate(LocalDateTime.now());

        return comment;
    }
}
