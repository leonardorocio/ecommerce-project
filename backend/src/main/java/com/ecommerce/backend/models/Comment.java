package com.ecommerce.backend.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Builder
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotNull
    @NotBlank
    private String text;

    @NotNull
    @NotBlank
    private Integer rating;

    @ManyToOne
    @JoinColumn(name = "product_id")
    @JsonIgnore
    private Product product_rated;

    @ManyToOne
    @JoinColumn(name = "user_id")
    @JsonIgnore
    private User user_owner;
}
