package com.uolhost.apitest.services;

import com.uolhost.apitest.models.user.User;
import com.uolhost.apitest.models.user.UserFormDto;
import com.uolhost.apitest.models.user.UserViewDto;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.AutoConfigureTestEntityManager;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Profile;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class UserServiceTest {

    @Autowired
    private UserService userService;

    @Test
    void shouldReturnListAllUsers() {
        List<UserViewDto> users = userService.index();

        assertEquals(users.size(), 3);
        assertEquals(users.get(0).name(), "teste");
    }

    @Test
    void shouldCreateUserWithValidInputAndLigaCodeName() {
        UserViewDto user = userService.create(
                new UserFormDto(
                        "teste4",
                        "teste4@email.com",
                        "11908765454",
                        "liga"
                )
        );

        assertEquals(user.id(), 4);
        assertEquals(user.name(), "teste4");
    }

    @Test
    void shouldCreateUserWithValidInputAndVingadoresCodeName() {
        UserViewDto user = userService.create(
                new UserFormDto(
                        "teste4",
                        "teste4@email.com",
                        "11908765454",
                        "vingadores")
        );

        assertEquals(user.id(), 4);
        assertEquals(user.name(), "teste4");
    }
}