package br.edu.ufrn.ecommerce.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

@Service
public class StoreService {

    private static final String API_STORE = "http://localhost:8080";

    public JsonNode get(Integer id) throws IOException, InterruptedException {
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder(
                        URI.create(API_STORE + "/product/" + id))
                .header("accept", "application/json")
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        ObjectMapper mapper = new ObjectMapper();

        if (response.statusCode() == 200) {
            return mapper.readTree(response.body());
        } else {
            throw new RuntimeException("Erro na requisição: " + response.statusCode());
        }
    }
}
