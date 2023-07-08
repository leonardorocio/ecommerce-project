package com.ecommerce.backend.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDate;
import java.util.Date;

@Entity
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Shipment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column
    @NotNull
    private boolean delivered;

    @Column
    @NotNull
    private double shippingPrice;

    @Column
    @NotNull
    private LocalDate expectedDeliveryDate;
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "shipment_order", referencedColumnName = "id")
    @NotNull
    @JsonIgnore
    private Orders orders;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "shipper", referencedColumnName = "id")
    @NotNull
    private Shipper shipper;
}
