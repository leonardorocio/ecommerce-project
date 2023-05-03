package com.ecommerce.backend.services;

import com.ecommerce.backend.exceptions.ResourceNotFoundException;
import com.ecommerce.backend.mapper.CommentMapper;
import com.ecommerce.backend.models.Comment;
import com.ecommerce.backend.repository.CommentRepository;
import com.ecommerce.backend.payload.CommentPostRequestBody;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
@RequiredArgsConstructor
@Log4j2
public class CommentService {
    private final CommentRepository commentRepository;
    private final UserService userService;
    private final ProductService productService;

    private final CommentMapper commentMapper;

    public Comment getCommentById(Integer id) {
        return commentRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("No such comment for this id")
        );
    }

    public List<Comment> getCommentsByUser(Integer id) {
        return commentRepository.getCommentsByUser(id).orElseThrow(
                () -> new ResourceNotFoundException("No comments from this user")
        );
    }

    public List<Comment> getCommentsByProduct(Integer id) {
        return commentRepository.getCommentsByProduct(id).orElseThrow(
                () -> new ResourceNotFoundException("There are no comments on this product")
        );
    }

    public Comment createComment(CommentPostRequestBody commentPostRequestBody) {
        Comment newComment = commentMapper.mapToComment(commentPostRequestBody);
        return commentRepository.save(newComment);
    }

    public void deleteComment(Integer id) {
        commentRepository.deleteById(id);
    }

    public Comment updateComment(CommentPostRequestBody commentPostRequestBody, Integer id) {
        Comment oldComment = getCommentById(id);
        Comment newComment = commentMapper.mapToComment(commentPostRequestBody);
        newComment.setCommentId(oldComment.getCommentId());
        return commentRepository.save(newComment);
    }
}
