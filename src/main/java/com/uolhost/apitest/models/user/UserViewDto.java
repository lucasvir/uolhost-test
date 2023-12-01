package com.uolhost.apitest.models.user;

public record UserViewDto(
        Long id,

        String name,
        String email,
        String telephone,
        String codename,
        String codenameGroup
) {
    public UserViewDto(User user) {
        this(
                user.getId(),
                user.getName(),
                user.getEmail(),
                user.getTelephone(),
                user.getCodename(),
                user.getCodenameGroup().toString()
        );
    }
}
