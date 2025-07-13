package org.dmiit3iy.ordermicroservice.model;

public enum Role {
    USER, ADMIN;

    public String getAuthority() {
        return name();
    }
}
