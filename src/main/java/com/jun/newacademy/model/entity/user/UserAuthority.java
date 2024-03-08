package com.jun.newacademy.model.entity.user;

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
        // 권한 이름 규칙은 "ROLE_"로 시작해야 함
        public static final String MANAGER = "ROLE_MANAGER";
        public static final String STAFF = "ROLE_STAFF";
    }
}
