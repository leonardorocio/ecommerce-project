package com.ecommerce.backend.repository;

import com.ecommerce.backend.models.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Integer> {

    @Query("SELECT comment FROM Comment comment WHERE comment.userOwner.id = ?1")
    public Optional<List<Comment>> getCommentsByUser(Integer id);

    @Query("SELECT comment FROM Comment comment WHERE comment.productRated.id = ?1")
    public Optional<List<Comment>> getCommentsByProduct(Integer id);
}
