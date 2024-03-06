package com.jun.newacademy.entity;

import lombok.Getter;

@Getter
public enum UserAuthority {

    MANAGER(Role.MANAGER),
    STAFF(Role.STAFF);

    private final String authority;

    UserAuthority(String authority) {
        this.authority = authority;
    }

    public static class Role {
        public static final String MANAGER = "MANAGER";
        public static final String STAFF = "STAFF";
    }
}
