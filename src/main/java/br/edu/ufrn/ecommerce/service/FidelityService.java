package br.edu.ufrn.ecommerce.service;

import br.edu.ufrn.ecommerce.dto.BonusRequestDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

@Service
public class FidelityService {

    private static final Logger logger = LoggerFactory.getLogger(FidelityService.class);

    public void aplicarBonus(@RequestBody BonusRequestDTO bonusRequestDTO) throws IOException, InterruptedException {

        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder(
                URI.create("htpp://localhost:8080/bonus"))
                .header("Accept", "application/json")
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        if(response.statusCode() != 200) {
            logger.error("Erro ao aplicar bonus");
        }
    }
}
