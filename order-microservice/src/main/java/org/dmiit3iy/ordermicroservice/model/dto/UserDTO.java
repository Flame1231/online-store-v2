package org.dmiit3iy.ordermicroservice.model.dto;

import lombok.Data;
import org.dmiit3iy.ordermicroservice.model.Role;
import org.dmiit3iy.ordermicroservice.model.User;

@Data
public class UserDTO {
    private Long id;
    private String username;
    private String email;
    private Role role;

    public UserDTO(User user) {
        this.id = user.getId();
        this.username = user.getUsername();
        this.email = user.getEmail();
        this.role = user.getRole();
    }

}