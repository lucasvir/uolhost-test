package com.uolhost.apitest.services;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import com.uolhost.apitest.exceptions.ResourceEmptyException;
import com.uolhost.apitest.exceptions.UserAlreadyRegisteredException;
import com.uolhost.apitest.models.user.User;
import com.uolhost.apitest.models.user.UserFormDto;
import com.uolhost.apitest.models.user.UserViewDto;
import com.uolhost.apitest.repositories.UserRepository;

import com.uolhost.apitest.utils.CodenameBuild;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;


@Service
public class UserService {

    @Autowired
    private UserRepository repository;

    public UserViewDto create(UserFormDto formDto) {
        boolean existsByEmail = repository.existsByEmail(formDto.email());
        if (existsByEmail) {
            throw new UserAlreadyRegisteredException("{ \"message\": \"Usuário já cadastrado.\" }");
        }

        CodenameBuild codenameBuild = new CodenameBuild(repository, formDto.codenameGroup());
        String codename = codenameBuild.getCodename();

        User user = repository.save(new User(formDto, codename));

        return new UserViewDto(user);
    }


    public List<UserViewDto> index() {
        List<User> users = repository.findAll();
        if (users.isEmpty()) {
            throw new ResourceEmptyException("{ \"message\": \"Não há registros\" }");
        }

        List<UserViewDto> usersDto = new ArrayList<>();
        users.forEach(u -> usersDto.add(new UserViewDto(u)));

        return usersDto;
    }
}
