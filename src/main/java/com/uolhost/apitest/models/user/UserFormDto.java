package com.uolhost.apitest.models.user;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

public record UserFormDto(
        @NotNull
        String name,

        @NotNull
        String email,

        @NotNull
//        @Pattern(regexp = "(\\([0-9]{2}\\))([0-9]{4,5})([0-9]{4})")
        String telephone,

        @NotNull
        String codenameGroup
) {
}
