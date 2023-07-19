package com.ecommerce.backend.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
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
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotNull
    @NotBlank
    @Column
    private String name;

    @NotNull
    @NotBlank
    @Column
    private String shortDescription;

    @NotNull
    @NotBlank
    @Column(columnDefinition = "TEXT")
    private String detailedDescription;

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
    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL)
    private List<OrderDetails> orderDetails;

    @OneToMany(mappedBy = "productRated", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<Comment> comments;

    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<Favorite> favoriteList;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "productCategory")
    @NotNull
    private ProductCategory productCategory;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "brand_id", referencedColumnName = "id")
    @NotNull
    private Brand brand;

    @NotNull
    @Column(length = 512)
    private String productImage;
}
