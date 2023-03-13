package com.ecommerce.hardware.request;

import com.ecommerce.hardware.models.Product;
import com.ecommerce.hardware.models.User;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CommentPostRequestBody {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotNull
    @NotBlank
    private String text;

    @NotNull
    @NotBlank
    private Integer rating;

    @NotNull
    private Integer product_rated;

    @NotNull
    private Integer user_owner;
}
