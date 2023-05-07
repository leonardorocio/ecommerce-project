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
    private Integer commentId;

    @NotNull
    @NotBlank
    @Column
    private String text;

    @NotNull
    @Column
    private Integer rating;

    @ManyToOne
    @JoinColumn(name = "product_id", referencedColumnName = "productId")
    @JsonIgnore
    private Product productRated;

    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "userId")
    @JsonIgnore
    private User userOwner;
}
