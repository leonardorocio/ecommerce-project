package com.ecommerce.backend.models;

import jakarta.persistence.*;
import lombok.*;

import java.util.Date;

@Entity
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Shipment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int shipmentId;

    @Column
    private boolean delivered;

    @Column
    private Date expectedDeliveryDate;
    @OneToOne
    @JoinColumn(name = "order")
    private Order order;
}
