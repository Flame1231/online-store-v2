package org.dmiit3iy.ordermicroservice.model;

public enum Role {
    ROLE_USER, ROLE_ADMIN;

    public String getAuthority() {
        return name();
    }
}
