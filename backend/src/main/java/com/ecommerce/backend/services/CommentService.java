package com.ecommerce.backend.services;

import com.ecommerce.backend.exceptions.ResourceNotFoundException;
import com.ecommerce.backend.mapper.CommentMapper;
import com.ecommerce.backend.models.Comment;
import com.ecommerce.backend.models.Product;
import com.ecommerce.backend.models.User;
import com.ecommerce.backend.repository.CommentRepository;
import com.ecommerce.backend.payload.CommentPostRequestBody;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
@Log4j2
public class CommentService {

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private ProductService productService;
    @Autowired
    private CommentMapper commentMapper;

    public Comment getCommentById(Integer id) {
        return commentRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Nenhum comentário com esse ID")
        );
    }

    public List<Comment> getComments() {
        return commentRepository.findAll();
    }

    public List<Comment> getCommentsByUser(Integer id) {
        User user = userService.getUserById(id);
        return user.getComments();
    }

    public List<Comment> getCommentsByProduct(Integer id) {
        Product product = productService.getProductById(id);
        return product.getComments();
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
        newComment.setId(oldComment.getId());
        return commentRepository.save(newComment);
    }
}
