package com.blog.blogapi.service;

import com.blog.blogapi.application.commentcreate.CreateCommentRequest;
import com.blog.blogapi.application.commentcreate.CreateCommentResult;
import com.blog.blogapi.application.commentlike.LikeCommentRequest;
import com.blog.blogapi.data.CommentLikeRepository;
import com.blog.blogapi.data.CommentRepository;
import com.blog.blogapi.domain.assembler.CommentAssembler;
import com.blog.blogapi.domain.model.Comment;
import com.blog.blogapi.domain.model.CommentLike;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class CommentService {
    private final CommentRepository commentRepository;
    private final CommentAssembler commentAssembler;
    private final CommentLikeRepository commentLikeRepository;

    public List<Comment> getAllComments() {
        log.info("Get All Comment started");
        try {
            return commentRepository.findAll();
        } catch (Exception ex) {
            log.error("Get All Comment failed with error: {}", ex.getMessage());
            return null;
        }
    }

    public CreateCommentResult create(CreateCommentRequest request) {
        log.info("Create comment with user: {}", request.getUsername());
        try {
            Comment comment = commentAssembler.applyCommentWithCreateRequest(request);
            commentRepository.save(comment);

            log.info("Created comment with user: {}", request.getUsername());
            return new CreateCommentResult(true, "Yorum Oluşturuldu.");
        } catch (Exception ex) {
            log.error("Create comment error with user: {}, error: {}", request.getUsername(), ex.getMessage());
            return new CreateCommentResult(false, ex.getMessage());
        }
    }

    public boolean delete(Long commentId) {
        log.info("Delete comment with commentId {}", commentId);
        try {
            Comment comment = commentRepository.findById(commentId).orElse(null);
            if (comment == null) {
                log.warn("Comment with commentId {} not found.", commentId);
                return false;
            }
            commentRepository.delete(comment);

            log.info("Deleted comment with commentId {}", commentId);
            return true;
        } catch (Exception ex) {
            log.error("Delete comment error with commentId {}, error: {}", commentId, ex.getMessage());
            return false;
        }
    }

    public boolean like(LikeCommentRequest request) {
        log.info("Like comment with commentId {}, username: {}", request.getCommentId(), request.getUsername());
        try {
            Comment comment = commentRepository.findById(request.getCommentId()).orElse(null);
            if (comment == null) {
                return false;
            }

            if (commentLikeRepository.existsByUsernameAndCommentId(request.getUsername(), request.getCommentId())) {
                comment.setLikeCount(comment.getLikeCount() - 1);
                commentRepository.save(comment);
                commentLikeRepository.delete(commentLikeRepository.findByUsernameAndCommentId(request.getUsername(), request.getCommentId()));

                log.info("Unliked comment with commentId {} and username {}", request.getCommentId(), request.getUsername());
                return true;
            }

            comment.setLikeCount(comment.getLikeCount() + 1);
            commentRepository.save(comment);
            commentLikeRepository.save(new CommentLike(request.getCommentId(), request.getUsername()));

            log.info("Liked comment with commentId {} and username {}", request.getCommentId(), request.getUsername());
            return true;
        } catch (Exception ex) {
            log.error("Like comment error with commentId {}, username: {}, error: {}", request.getCommentId(), request.getUsername(), ex.getMessage());
            return false;
        }
    }
}
