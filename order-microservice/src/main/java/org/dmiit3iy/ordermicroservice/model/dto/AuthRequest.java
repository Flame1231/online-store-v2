package org.dmiit3iy.ordermicroservice.model.dto;

import lombok.Data;

@Data
public class AuthRequest {
    private String username;
    private String password;
}
