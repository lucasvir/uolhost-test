package com.uolhost.apitest.utils;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.uolhost.apitest.exceptions.ResourceEmptyException;
import com.uolhost.apitest.models.user.User;
import com.uolhost.apitest.repositories.UserRepository;
import lombok.NoArgsConstructor;
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;
import org.springframework.stereotype.Service;

import java.io.*;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.List;

@Service
@NoArgsConstructor
public class CodenameBuild {

    private UserRepository repository;

    private String codenameGroup;

    public CodenameBuild(UserRepository repository, String codenameGroup) {
        this.repository = repository;
        this.codenameGroup = codenameGroup;
    }

    private String requestCodenames() {
        var url = codenameGroup.equalsIgnoreCase("vingadores") ?
                "https://raw.githubusercontent.com/uolhost/test-backEnd-Java/master/referencias/vingadores.json"
                : "https://raw.githubusercontent.com/uolhost/test-backEnd-Java/master/referencias/liga_da_justica.xml";

        var client = HttpClient.newHttpClient();

        try {
            HttpRequest request = HttpRequest.newBuilder(
                            URI.create(url))
                    .header("accept", "application/json")
                    .header("accept", "application/xml")
                    .build();

            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            return response.body();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        return null;
    }

    private List<String> getJsonCodenameList(String requestCodenames) {
        JsonObject responseJsonObject = JsonParser.parseString(requestCodenames).getAsJsonObject();
        JsonArray codenameJsonArray = responseJsonObject.get("vingadores").getAsJsonArray();

        List<String> codenomesList = new ArrayList<>();
        codenameJsonArray.forEach(codename -> {
            var object = codename.getAsJsonObject();
            var codenameObject = object.get("codinome");
            codenomesList.add(codenameObject.getAsString());
        });

        return codenomesList;
    }

    private List<String> getXmlCodenameList(String requestCodenames) {
        List<String> codenamesList = new ArrayList<>();

        try {
            SAXBuilder builder = new SAXBuilder();
            Document document = builder.build(new StringReader(requestCodenames));
            Element root = document.getRootElement();
            List<Element> codinomes = root.getChild("codinomes").getChildren();

            codinomes.forEach(c -> {
                codenamesList.add(c.getValue());
            });


        } catch (JDOMException | IOException e) {
            throw new RuntimeException("Error parsing xml response. " + "error: " + e.getMessage());
        }

        return codenamesList;
    }

    public String getCodename() {
        //making a list with users codename already registered
        List<User> users = repository.findAll();
        List<String> usersCodenames = new ArrayList<>();
        users.forEach(user -> usersCodenames.add(user.getCodename()));

        // getting codename from a request from outside url
        String requestCodenames = requestCodenames();

        boolean codenameGroupIsVingadores = codenameGroup.equalsIgnoreCase("vingadores");
        List<String> requestCodenameList = codenameGroupIsVingadores
                ? getJsonCodenameList(requestCodenames)
                : getXmlCodenameList(requestCodenames);

        List<String> usableCodenameList = requestCodenameList.stream()
                .filter(codename -> !usersCodenames.contains(codename))
                .toList();

        if (usableCodenameList.isEmpty()) {
            throw new ResourceEmptyException("Não há mais codinomes disponíveis.");
        }

        int n = usableCodenameList.size();
        int random = (int) (Math.random() * n);

        return usableCodenameList.get(random);
    }
}
