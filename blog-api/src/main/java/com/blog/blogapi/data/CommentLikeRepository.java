package com.blog.blogapi.data;

import com.blog.blogapi.domain.model.CommentLike;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CommentLikeRepository extends JpaRepository<CommentLike, Long> {
    boolean existsByUsernameAndCommentId(String username, Long commentId);
    CommentLike findByUsernameAndCommentId(String username, Long commentId);
}
