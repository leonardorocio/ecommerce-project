package com.ecommerce.backend.models;

import jakarta.persistence.*;
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
    private LocalDate orderedDate;

    @Column
    private double totalPrice;

    @ManyToOne
    @JoinColumn(name = "user_orders", referencedColumnName = "userId")
    private User customer;

    private boolean closed;

    @OneToMany(mappedBy = "orders", cascade = CascadeType.ALL)
    private List<OrderDetails> orderDetailsId;

    @OneToOne(mappedBy = "orders", cascade = CascadeType.ALL)
    private Shipment shipment;
}
