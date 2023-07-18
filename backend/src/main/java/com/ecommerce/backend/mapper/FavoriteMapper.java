package com.ecommerce.backend.mapper;

import com.ecommerce.backend.models.Favorite;
import com.ecommerce.backend.payload.FavoriteRequestBody;
import com.ecommerce.backend.services.ProductService;
import com.ecommerce.backend.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class FavoriteMapper {

    private final UserService userService;

    private final ProductService productService;

    public Favorite mapToFavorite(FavoriteRequestBody favoriteRequestBody) {
        Favorite favorite = Favorite.builder()
                .user(userService.getUserById(favoriteRequestBody.getUserId()))
                .product(productService.getProductById(favoriteRequestBody.getProductId()))
                .build();
        return favorite;
    }
}
