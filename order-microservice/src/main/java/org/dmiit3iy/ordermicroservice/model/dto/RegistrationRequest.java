package org.dmiit3iy.ordermicroservice.model.dto;

import lombok.Data;

@Data
public class RegistrationRequest {
    private String username;
    private String password;
    private String email;
}