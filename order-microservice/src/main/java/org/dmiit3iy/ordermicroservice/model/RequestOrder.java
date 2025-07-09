package org.dmiit3iy.ordermicroservice.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RequestOrder {
    private String name;
    private int quantity;
    private int userId;
}
