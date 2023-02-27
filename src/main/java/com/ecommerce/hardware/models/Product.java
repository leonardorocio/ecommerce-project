package com.ecommerce.hardware.models;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
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
    private Integer product_id;

    @NotNull
    private String name;

    @NotNull
    private String description;

    @NotNull
    private double price;

    @NotNull
    private int stock;

    @Column(columnDefinition = "integer default 1.0")
    private double discount = 1.0;

    @JsonIgnore
    @ManyToMany(mappedBy = "productList", cascade = CascadeType.ALL)
    private List<User> userList;

    @OneToMany(mappedBy = "product_rated", cascade = CascadeType.ALL)
    private List<Comment> comments;

}
