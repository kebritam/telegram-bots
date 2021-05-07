// SPDX-License-Identifier: MIT

package com.teimour.insult;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

/**
 * @author kebritam
 * Project echo-bot
 */

public class InsultDataApi implements InsultData {

    private final URI uri;
    private final ObjectMapper objectMapper;

    public InsultDataApi() {
        this.objectMapper = new ObjectMapper();
        uri = URI.create("https://evilinsult.com/generate_insult.php?type=json");
    }

    @Override
    public String getInsult() {
        return getInsultField();
    }

    private String getInsultField() {
        JsonNode node;
        try {
            node = objectMapper.readTree(getContentAsString());
        } catch (JsonProcessingException e) {
            throw new IllegalStateException("there is a problem with json parsing");
        }
        return node.path("insult").asText();
    }

    private String getContentAsString() {
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .GET()
                .uri(uri)
                .build();

        HttpResponse<String> response;
        try {
            response = client.send(request, HttpResponse.BodyHandlers.ofString());
        } catch (IOException | InterruptedException e) {
            throw new RuntimeException("there is a problem with web-api connection");
        }
        return response.body();
    }
}
