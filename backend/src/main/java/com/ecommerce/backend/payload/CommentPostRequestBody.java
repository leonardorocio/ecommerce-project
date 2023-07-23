package com.ecommerce.backend.payload;

import com.ecommerce.backend.models.Product;
import com.ecommerce.backend.models.User;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonTypeId;
import com.fasterxml.jackson.annotation.JsonValue;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.media.SchemaProperty;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
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
    @Schema(name = "text", description = "Comment's Text", example = "Produto muito bom, ótima qualidade e desempenho", format = "text")
    private String text;

    @NotNull
    @Schema(name = "rating", description = "Comment's Rating", example = "5")
    private Integer rating;

    @NotNull
    @Schema(name = "productRated", description = "The ID of the Product you are rating",
            ref = "Product")
    private Integer productRated;

    @NotNull
    @Schema(name = "userOwner", description = "The ID of the User", ref = "User")
    private Integer userOwner;
}
