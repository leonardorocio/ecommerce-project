package com.ecommerce.backend.controller;


import com.ecommerce.backend.models.Comment;
import com.ecommerce.backend.payload.CommentPostRequestBody;
import com.ecommerce.backend.services.CommentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
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
@Tag(name = "Comments", description = "Describes the comment related operations")
@SecurityRequirement(name = "Bearer Authentication")
public class CommentController {

    @Autowired
    private CommentService commentService;

    @GetMapping(path = "/user/{id}")
    @Operation(summary = "Returns all the comments from a user",
            description = "Takes an user's id and returns all of it's comments",
            tags = {"Comment, User"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Returns the list of comments of the user"),
            @ApiResponse(responseCode = "400", description = "User not found"),
            @ApiResponse(responseCode = "401", description = "Authentication failed")
    })
    public ResponseEntity<List<Comment>> getCommentsByUser(@PathVariable Integer id) {
        return ResponseEntity.ok(commentService.getCommentsByUser(id));
    }

    @GetMapping(path = "/product/{id}")
    @Operation(summary = "Returns all the comments from a product",
            description = "Takes a product's id and returns all of it's comments",
            tags = {"Comment, Product"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Returns the list of comments of the product"),
            @ApiResponse(responseCode = "400", description = "Product not found"),
            @ApiResponse(responseCode = "401", description = "Authentication failed")
    })
    public ResponseEntity<List<Comment>> getCommentsByProduct(@PathVariable Integer id) {
        return ResponseEntity.ok(commentService.getCommentsByProduct(id));
    }

    @PostMapping
    @Operation(summary = "Posts a comment",
            description = "Takes a CommentRequestBody, maps to a Comment and saves in the database")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Returns comment saved in the database"),
            @ApiResponse(responseCode = "400", description = "Invalid Argument"),
            @ApiResponse(responseCode = "404", description = "User not found"),
            @ApiResponse(responseCode = "404", description = "Product not found"),
            @ApiResponse(responseCode = "401", description = "Authentication failed")
    })
    public ResponseEntity<Comment> postComment(@RequestBody @Valid CommentPostRequestBody commentPostRequestBody) {
        return new ResponseEntity<>(commentService.createComment(commentPostRequestBody), HttpStatus.CREATED);
    }

    @DeleteMapping(path = "/{id}")
    @Operation(summary = "Deletes a comment",
            description = "Takes the comment's id you want to delete")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Comment deleted successfully"),
            @ApiResponse(responseCode = "404", description = "Comment not found"),
            @ApiResponse(responseCode = "401", description = "Authentication failed")
    })
    public ResponseEntity<Void> deleteComment(@PathVariable Integer id) {
        commentService.deleteComment(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Updates a comment",
            description = "Takes the comment's id you want to update")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Returns updated comment"),
            @ApiResponse(responseCode = "404", description = "Comment not found"),
            @ApiResponse(responseCode = "401", description = "Authentication failed")
    })
    public ResponseEntity<Comment> updateComment(@RequestBody @Valid CommentPostRequestBody commentPostRequestBody,
                                                 @PathVariable Integer id) {
        return ResponseEntity.ok(commentService.updateComment(commentPostRequestBody, id));
    }
}
