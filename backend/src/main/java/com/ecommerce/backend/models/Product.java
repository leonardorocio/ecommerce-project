package com.ecommerce.backend.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Builder
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer productId;

    @NotNull
    @NotBlank
    @Column
    private String name;

    @NotNull
    @NotBlank
    @Column
    private String description;

    @NotNull
    @Column
    private double price;

    @NotNull
    @Column
    private int stock;

    @Column(columnDefinition = "float default 1.0")
    @NotNull
    private double discount = 1.0;

    @JsonIgnore
    @OneToMany(mappedBy = "product")
    private List<OrderDetails> orderDetails;

    @OneToMany(mappedBy = "productRated", cascade = CascadeType.ALL)
    private List<Comment> comments;

    @ManyToOne
    @JoinColumn(name = "productCategory")
    private ProductCategory productCategory;

    @Column
    private String imageURL;
}
