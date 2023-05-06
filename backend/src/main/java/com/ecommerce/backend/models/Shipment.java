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
    private Integer shipmentId;

    @Column
    private boolean delivered;

    @Column
    private Date expectedDeliveryDate;
    @OneToOne
    @JoinColumn(name = "shipment_order", referencedColumnName = "orderId")
    private Orders orders;
}
