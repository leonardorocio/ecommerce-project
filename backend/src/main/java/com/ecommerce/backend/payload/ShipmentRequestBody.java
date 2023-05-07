package com.ecommerce.backend.payload;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

@Data
public class ShipmentRequestBody {

    @NotNull
    private double shippingPrice;

    @NotNull
    private boolean delivered;

    @NotNull
    private LocalDate expectedDeliveryDate;

    @NotNull
    private int orderId;
}
