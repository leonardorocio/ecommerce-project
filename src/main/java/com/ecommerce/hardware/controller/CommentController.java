package com.ecommerce.hardware.controller;


import com.ecommerce.hardware.models.Comment;
import com.ecommerce.hardware.request.CommentPostRequestBody;
import com.ecommerce.hardware.services.CommentService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/comments")
public class CommentController {

    @Autowired
    private CommentService commentService;

    @GetMapping(path = "/user/{id}")
    public ResponseEntity<List<Comment>> getCommentsByUser(@PathVariable Integer id) {
        return ResponseEntity.ok(commentService.getCommentsByUser(id));
    }

    @GetMapping(path = "/product/{id}")
    public ResponseEntity<List<Comment>> getCommentsByProduct(@PathVariable Integer id) {
        return ResponseEntity.ok(commentService.getCommentsByProduct(id));
    }

    @PostMapping
    public ResponseEntity<Comment> postComment(@RequestBody CommentPostRequestBody commentPostRequestBody) {
        return new ResponseEntity<>(commentService.createComment(commentPostRequestBody), HttpStatus.CREATED);
    }

    @DeleteMapping(path = "/{id}")
    public ResponseEntity<Void> deleteComment(@PathVariable Integer id) {
        commentService.deleteComment(id);
        return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
    }

    @PutMapping
    public ResponseEntity<Comment> updateComment(@RequestBody CommentPostRequestBody commentPostRequestBody) {
        return ResponseEntity.ok(commentService.updateComment(commentPostRequestBody));
    }
}
