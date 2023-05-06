package com.ecommerce.backend.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Entity
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer orderDetailsId;

    @Column
    @NotNull
    private int quantity;

    @ManyToOne
    @JoinColumn(name = "order_id", referencedColumnName = "orderId")
    @JsonIgnore
    @NotNull
    private Orders orders;

    @ManyToOne
    @JoinColumn(name = "product_id", referencedColumnName = "productId")
    @NotNull
    private Product product;
}
