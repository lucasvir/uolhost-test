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

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.List;


@Service
public class UserService {

    @Autowired
    private UserRepository repository;

    public UserViewDto create(UserFormDto formDto) {
        boolean existsByEmail = repository.existsByEmail(formDto.email());
        if (existsByEmail) {
            throw new UserAlreadyRegisteredException("Usuário já cadastrado.");
        }

        String codename = getCodename(formDto.codenameGroup());
        User user = repository.save(new User(formDto, codename));

        return new UserViewDto(user);
    }


    public List<UserViewDto> index() {
        List<User> users = repository.findAll();
        if (users.isEmpty()) {
            throw new ResourceEmptyException("Não há registros");
        }

        List<UserViewDto> usersDto = new ArrayList<>();
        users.forEach(u -> usersDto.add(new UserViewDto(u)));

        return usersDto;
    }

    private String requestCodenames(String group) {
        var url = group.equalsIgnoreCase("vingadores") ?
                "https://raw.githubusercontent.com/uolhost/test-backEnd-Java/master/referencias/vingadores.json"
                : "https://raw.githubusercontent.com/uolhost/test-backEnd-Java/master/referencias/liga_da_justica.xml";

        var client = HttpClient.newHttpClient();

        try {
            HttpRequest request = HttpRequest.newBuilder(
                            URI.create(url))
                    .header("accept", "application/json")
                    .build();

            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            return response.body();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        return null;
    }

    private List<String> getCodenameList(String responseGroup) {
        JsonObject responseJsonObject = JsonParser.parseString(responseGroup).getAsJsonObject();
        JsonArray codinomesJsonArray = responseJsonObject.get("vingadores").getAsJsonArray();

        List<String> codenomesList = new ArrayList<>();
        codinomesJsonArray.forEach(c -> {
            var object = c.getAsJsonObject();
            var codename = object.get("codinome");
            codenomesList.add(codename.getAsString());
        });

        return codenomesList;
    }

    private String getCodename(String codeGroup) {
        List<User> users = repository.findAll();
        List<String> usersCodenames = new ArrayList<>();
        users.forEach(user -> usersCodenames.add(user.getCodename()));

        String requestCodenames = requestCodenames(codeGroup);
        List<String> requestCodenameList = getCodenameList(requestCodenames);

        List<String> usableCodenameList = requestCodenameList.stream()
                .filter(codename -> !usersCodenames.contains(codename))
                .toList();

        int n = usableCodenameList.size();
        int random = (int) (Math.random() * n);

        return usableCodenameList.get(random);
    }
}
