package com.ecommerce.backend.payload;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CommentPostRequestBody {

    @NotNull
    @NotBlank
    private String text;

    @NotNull
    private Integer rating;

    @NotNull
    private Integer product_rated;

    @NotNull
    private Integer user_owner;
}
