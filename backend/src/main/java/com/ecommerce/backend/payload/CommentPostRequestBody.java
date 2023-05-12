package com.ecommerce.backend.payload;

import io.swagger.v3.oas.annotations.media.Schema;
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
    @Schema(name = "Text", description = "Comment's Text", example = "Produto muito bom, ótima qualidade e desempenho")
    private String text;

    @NotNull
    @Schema(name = "Rating", description = "Comment's Rating", example = "5")
    private Integer rating;

    @NotNull
    @Schema(name = "ProductRated", description = "The ID of the Product you are rating", example = "1")
    private Integer product_rated;

    @NotNull
    @Schema(name = "UserOwner", description = "The ID of the User which owns the comment", example = "1")
    private Integer user_owner;
}
