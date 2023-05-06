package com.ecommerce.backend.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
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
    private double shippingPrice;

    @Column
    private Date expectedDeliveryDate;
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "shipment_order", referencedColumnName = "orderId")
    private Orders orders;
}
