package com.ecommerce.backend.models;

import jakarta.persistence.*;
import lombok.*;

import java.util.Date;
import java.util.List;

@Entity
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int orderId;

    @Column
    private Date orderedDate;

    @Column
    private double totalPrice;

    @ManyToOne
    @JoinColumn(name = "userOrders")
    private User customer;

    private boolean closed;

    @OneToMany(mappedBy = "order")
    private List<OrderDetails> orderDetailsId;

    @OneToOne(mappedBy = "order")
    private Shipment shipment;
}
