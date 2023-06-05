package com.ecommerce.backend.repository;

import com.ecommerce.backend.models.Address;
import com.ecommerce.backend.models.Comment;
import jakarta.transaction.Transactional;
import jakarta.validation.ConstraintViolationException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@DisplayName("Tests for Comment repository")
class CommentRepositoryTest {

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ProductRepository productRepository;

    @Test
    @DisplayName("Save persists a Comment when successful")
    public void save_PersistComment_WhenSuccessful() {
        Comment mockComment = createMockComment();
        Comment commentSaved = commentRepository.save(mockComment);

        Assertions.assertNotNull(commentSaved);
        Assertions.assertEquals(commentSaved.getRating(), commentSaved.getRating());
        Assertions.assertNotNull(commentSaved.getCommentId());
    }

    @Test
    @DisplayName("Save throws error for an Comment when failed")
    public void save_ThrowsErrorComment_WhenFailed() {
        Comment commentToSave = createMockComment();
        commentToSave.setRating(null);
        Assertions.assertThrows(ConstraintViolationException.class, () -> {
            this.commentRepository.save(commentToSave);
        });
    }

    @Test
    @DisplayName("Save updates a Comment when successful")
    public void save_UpdatesComment_WhenSuccessful() {
        Comment commentToSave = createMockComment();
        Comment commentSaved = commentRepository.save(commentToSave);

        commentSaved.setRating(3);
        Comment updatedComment = commentRepository.save(commentSaved);

        Assertions.assertNotNull(updatedComment);
        Assertions.assertEquals(commentSaved.getRating(), updatedComment.getRating());
        Assertions.assertNotNull(updatedComment.getCommentId());
    }

    @Test
    @DisplayName("Delete removes a Comment when successful")
    public void delete_RemovesComment_WhenSuccessful() {
        Comment commentToSave = createMockComment();
        Comment commentSaved = commentRepository.save(commentToSave);

        commentRepository.delete(commentSaved);

        Optional<Comment> commentOptional = commentRepository.findById(commentSaved.getCommentId());

        Assertions.assertEquals(commentOptional.isEmpty(), true);
    }

    @Test
    @DisplayName("Lists a Comment list by user id when successful")
    @Transactional
    public void find_ListUserComment_WhenSuccessful() {
        Comment commentToSave = createMockComment();
        Comment commentSaved = commentRepository.save(commentToSave);

        List<Comment> commentList = commentRepository.getCommentsByUser(3).get();

        Assertions.assertEquals(commentList.isEmpty(), false);
        Assertions.assertEquals(commentList.contains(commentSaved), true);
    }

    @Test
    @DisplayName("Lists a Comment list by product id when successful")
    @Transactional
    public void find_ListProductComment_WhenSuccessful() {
        Comment commentToSave = createMockComment();
        Comment commentSaved = commentRepository.save(commentToSave);

        List<Comment> commentList = commentRepository.getCommentsByProduct(1).get();

        Assertions.assertEquals(commentList.isEmpty(), false);
        Assertions.assertEquals(commentList.contains(commentSaved), true);
    }


    private Comment createMockComment() {
        Comment comment = Comment.builder()
                .productRated(productRepository.findById(1).get())
                .userOwner(userRepository.findById(3).get())
                .rating(5)
                .text("Gatica Bulicas")
                .build();
        return comment;
    }
}