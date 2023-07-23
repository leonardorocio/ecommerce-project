package com.ecommerce.backend.mapper;

import com.ecommerce.backend.exceptions.BadRequestException;
import com.ecommerce.backend.models.Comment;
import com.ecommerce.backend.models.Product;
import com.ecommerce.backend.models.User;
import com.ecommerce.backend.payload.CommentPostRequestBody;
import com.ecommerce.backend.services.ProductService;
import com.ecommerce.backend.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CommentMapper {

    private final UserService userService;

    private final ProductService productService;

    public Comment mapToComment(CommentPostRequestBody commentPostRequestBody) {
        if (validateComment(commentPostRequestBody)) {
            User userOwner = userService.getUserById(commentPostRequestBody.getUserOwner());
            Product productRated = productService.getProductById(commentPostRequestBody.getProductRated());
            Comment comment = Comment.builder()
                    .productRated(productRated)
                    .text(commentPostRequestBody.getText())
                    .rating(commentPostRequestBody.getRating())
                    .userOwner(userOwner)
                    .build();
            return comment;
        }
        return null;
    }

    public boolean validateComment(CommentPostRequestBody commentPostRequestBody) {
        if (commentPostRequestBody.getRating() > 5 || commentPostRequestBody.getRating() < 0) {
            throw new BadRequestException("Nota de avaliação não pode ser menor que 0 e nem maior que 5");
        }
        return true;
    }
}
