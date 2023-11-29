package com.uolhost.apitest.models.user;

import com.uolhost.apitest.models.enums.Group;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Objects;

@Entity
@Table(name = "users")
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class User implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    private String name;

    @NotNull
    @Email
    private String email;

    @NotNull
    private String telephone;

    @NotNull
    private String codename;

    @NotNull
    @Enumerated(EnumType.STRING)
    private Group codenameGroup;

    public User(String name, String email, String telephone, String codename, Group codenameGroup) {
        this.name = name;
        this.email = email;
        this.telephone = telephone;
        this.codename = codename;
        this.codenameGroup = codenameGroup;
    }

    public User(UserFormDto formDto, String codename) {
        this.name = formDto.name();
        this.email = formDto.email();
        this.telephone = formDto.telephone();
        this.codename = codename;
        this.codenameGroup = Group.valueOf(formDto.codenameGroup().toUpperCase());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equals(id, user.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}


