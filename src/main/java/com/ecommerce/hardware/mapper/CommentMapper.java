package com.ecommerce.hardware.mapper;

import com.ecommerce.hardware.models.Comment;
import com.ecommerce.hardware.models.Product;
import com.ecommerce.hardware.models.User;
import com.ecommerce.hardware.request.CommentPostRequestBody;
import com.ecommerce.hardware.services.ProductService;
import com.ecommerce.hardware.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CommentMapper {

    private final UserService userService;

    private final ProductService productService;

    public Comment mapToComment(CommentPostRequestBody commentPostRequestBody) {
        User userOwner = userService.getUserById(commentPostRequestBody.getUser_owner());
        Product productRated = productService.getProductById(commentPostRequestBody.getProduct_rated());
        Comment comment = Comment.builder()
                .product_rated(productRated)
                .text(commentPostRequestBody.getText())
                .rating(commentPostRequestBody.getRating())
                .user_owner(userOwner)
                .build();
        return comment;
    }
}
