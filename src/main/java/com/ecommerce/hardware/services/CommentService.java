package com.ecommerce.hardware.services;

import com.ecommerce.hardware.exceptions.BadRequestException;
import com.ecommerce.hardware.exceptions.ResourceNotFoundException;
import com.ecommerce.hardware.mapper.CommentMapper;
import com.ecommerce.hardware.models.Comment;
import com.ecommerce.hardware.models.Product;
import com.ecommerce.hardware.models.User;
import com.ecommerce.hardware.repository.CommentRepository;
import com.ecommerce.hardware.request.CommentPostRequestBody;
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
                () -> new ResourceNotFoundException("There's no comments on this product")
        );
    }

    public Comment createComment(CommentPostRequestBody commentPostRequestBody) {
        Comment newComment = commentMapper.mapToComment(commentPostRequestBody);
        return commentRepository.save(newComment);
    }

    public void deleteComment(Integer id) {
        commentRepository.deleteById(id);
    }

    public Comment updateComment(CommentPostRequestBody commentPostRequestBody) {
        Comment oldComment = getCommentById(commentPostRequestBody.getId());
        Comment newComment = commentMapper.mapToComment(commentPostRequestBody);
        newComment.setId(oldComment.getId());
        return commentRepository.save(newComment);
    }
}
