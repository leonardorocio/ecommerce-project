package com.ecommerce.backend.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDate;
import java.util.List;

@Entity
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Orders {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer orderId;

    @Column
    @NotNull
    private LocalDate orderedDate;

    @Column
    @NotNull
    private double totalPrice;

    @ManyToOne
    @JoinColumn(name = "user_orders", referencedColumnName = "userId")
    private User customer;

    @Column
    @NotNull
    private boolean closed;

    @OneToMany(mappedBy = "orders", cascade = CascadeType.ALL)
    private List<OrderDetails> orderDetailsId;

    @OneToOne(mappedBy = "orders", cascade = CascadeType.ALL)
    @JsonIgnore
    private Shipment shipment;
}
