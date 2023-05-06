package com.ecommerce.backend.controller;


import com.ecommerce.backend.models.Comment;
import com.ecommerce.backend.payload.CommentPostRequestBody;
import com.ecommerce.backend.services.CommentService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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
    public ResponseEntity<Comment> postComment(@RequestBody @Valid CommentPostRequestBody commentPostRequestBody) {
        return new ResponseEntity<>(commentService.createComment(commentPostRequestBody), HttpStatus.CREATED);
    }

    @DeleteMapping(path = "/{id}")
    public ResponseEntity<Void> deleteComment(@PathVariable Integer id) {
        commentService.deleteComment(id);
        return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Comment> updateComment(@RequestBody @Valid CommentPostRequestBody commentPostRequestBody,
                                                 @PathVariable Integer id) {
        return ResponseEntity.ok(commentService.updateComment(commentPostRequestBody, id));
    }
}
