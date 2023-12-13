package com.uolhost.apitest.controllers;

import com.uolhost.apitest.exceptions.ResourceEmptyException;
import com.uolhost.apitest.models.user.UserFormDto;
import com.uolhost.apitest.repositories.UserRepository;
import com.uolhost.apitest.services.UserService;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.mockito.BDDMockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.AutoConfigureJsonTesters;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
@AutoConfigureMockMvc
@AutoConfigureJsonTesters
@Transactional
class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private JacksonTester<UserFormDto> userFormDtoJacksonTester;

    @Test
    void shouldReturn201ValidInputsWithVingadoresCodename() throws Exception {

        UserFormDto userFormDto = new UserFormDto(
                "teste4",
                "teste4@email.com",
                "11998876787",
                "VINGADORES"
        );

        String requestBodyJson = userFormDtoJacksonTester.write(userFormDto).getJson();

        var response = mockMvc.perform(
                        post("/users/form")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(requestBodyJson)
                )
                .andReturn().getResponse();

        String expectedHeaderLocation = "http://localhost/users/form/9";

        assertEquals(201, response.getStatus());
        assertEquals(expectedHeaderLocation, response.getHeader("Location"));
    }

    @Test
    void shouldReturn201ValidInputsWithLigaCodename() throws Exception {

        UserFormDto userFormDto = new UserFormDto(
                "teste5",
                "teste5@email.com",
                "11998876787",
                "liga"
        );

        String requestBodyJson = userFormDtoJacksonTester.write(userFormDto).getJson();

        var response = mockMvc.perform(
                        post("/users/form")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(requestBodyJson)
                )
                .andReturn().getResponse();

        String expectedHeaderLocation = "http://localhost/users/form/10";

        assertEquals(201, response.getStatus());
        assertEquals(expectedHeaderLocation, response.getHeader("Location"));
    }

    @Test
    void shouldReturn400UserAlreadyRegisteredByEmail() throws Exception {

        UserFormDto userFormDto = new UserFormDto(
                "teste",
                "teste@email.com",
                "11998876787",
                "liga"
        );

        String requestBodyJson = userFormDtoJacksonTester.write(userFormDto).getJson();

        var response = mockMvc.perform(
                        post("/users/form")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(requestBodyJson)
                )
                .andReturn().getResponse();

        assertEquals(400, response.getStatus());
    }

    @Test
    void shouldReturn200ListAllUsers() throws Exception {

        var response = mockMvc.perform(get("/users"))
                .andReturn().getResponse();

        assertEquals(200, response.getStatus());
    }

    @Test
    @Sql("/delete.sql")
    void shouldReturn404ListAllUsersResourceEmpty() throws Exception {
        var response = mockMvc.perform(get("/users"))
                .andReturn().getResponse();

        assertEquals(404, response.getStatus());
    }
}