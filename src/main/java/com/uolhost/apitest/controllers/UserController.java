package com.uolhost.apitest.controllers;

import com.uolhost.apitest.models.user.UserFormDto;
import com.uolhost.apitest.models.user.UserViewDto;
import com.uolhost.apitest.services.UserService;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

import static org.springframework.http.ResponseEntity.created;
import static org.springframework.http.ResponseEntity.ok;

@RestController
@RequestMapping("users")

public class UserController {

    @Autowired
    private UserService service;


    @GetMapping
    ResponseEntity<List<UserViewDto>> index() {
        List<UserViewDto> user = service.index();

        return ok(user);
    }

    @PostMapping("/form")
    @Transactional
    ResponseEntity<UserViewDto> create(@RequestBody @Valid UserFormDto formDto) {
        UserViewDto userDto = service.create(formDto);
        URI uri = ServletUriComponentsBuilder
                .fromCurrentRequestUri().path("/{id}").buildAndExpand(userDto.id()).toUri();

        return created(uri).body(userDto);
    }

    @DeleteMapping("/delete/{id}")
    @Transactional
    ResponseEntity<String> delete(@PathVariable String id) {
        service.delete(id);

        return ok("Usuário deletado com sucesso.");
    }
}
