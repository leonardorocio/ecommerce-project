package com.ecommerce.backend.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
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
    @NotNull
    private boolean delivered;

    @Column
    @NotNull
    private double shippingPrice;

    @Column
    @NotNull
    private Date expectedDeliveryDate;
    @OneToOne
    @JoinColumn(name = "shipment_order", referencedColumnName = "orderId")
    private Orders orders;
}
