package com.ecommerce.backend.payload;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

@Data
public class ShipmentRequestBody {

    @NotNull
    @Schema(name = "shippingPrice", description = "Shipment's price",
            example = "20.00")
    private double shippingPrice;

    @NotNull
    @Schema(name = "expectedDeliveryDate", description = "The shipments's expected delivery date",
            example = "2024-01-01")
    private LocalDate expectedDeliveryDate;

    @NotNull
    @Schema(name = "orderId", description = "The Id of the Order related to the Shipment",
            example = "1", ref = "Orders")
    private int orderId;

    @NotNull
    @Schema(name = "shipperId", description = "The Id of the Shipper related to the Shipment",
            example = "1", ref = "Shipper")
    private int shipperId;
}
