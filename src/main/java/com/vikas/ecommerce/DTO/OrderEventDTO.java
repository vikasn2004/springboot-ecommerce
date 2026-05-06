package com.vikas.ecommerce.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderEventDTO {
    private String orderId;
    private Long userId;
    private String status;
    private Double totalPrice;

    @Override
    public String toString() {
        return "OrderEventDTO{orderId=" + orderId +
                " , userId=" + userId +
                " , status=" + status +
                " , totalPrice=" + totalPrice + "}";
    }

}
