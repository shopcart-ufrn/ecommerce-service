package br.edu.ufrn.ecommerce.service;

import java.time.Duration;
import java.util.UUID;

import io.github.resilience4j.ratelimiter.RateLimiterConfig;
import io.github.resilience4j.ratelimiter.RateLimiterRegistry;
import io.github.resilience4j.ratelimiter.annotation.RateLimiter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import br.edu.ufrn.ecommerce.dto.request.StoreSellRequestDTO;
import br.edu.ufrn.ecommerce.dto.response.StoreProductResponseDTO;

@Service
public class StoreService {

    @Value("${store.baseUrl}")
    private  String baseUrl;

    private  WebClient client;

    private static final Logger logger = LoggerFactory.getLogger(ExchangeService.class);

    private WebClient getClient() {
        if (client == null) {
            client = WebClient.builder()
            .baseUrl(this.baseUrl)
            .build();
        }

        return this.client;

    }

    public StoreProductResponseDTO getProduct(Integer id) {
        String endpoint = "/product/{id}";
        WebClient client = this.getClient();
        StoreProductResponseDTO response;
        try {
            response = client
                    .get()
                    .uri(endpoint, id)
                    .retrieve()
                    .bodyToMono(StoreProductResponseDTO.class)
                    .timeout(Duration.ofSeconds(1))
                    .block();
            return response;
        } catch (Exception e) {
            throw new RuntimeException("Error retrieving product", e);
        }
    }

    private UUID postSell(StoreSellRequestDTO sellRequestDTO) {
        String endpoint = "/sell";
        WebClient client = this.getClient();
        UUID response = client
                .post()
                .uri(endpoint)
                .retrieve()
                .bodyToMono(UUID.class)
                .timeout(Duration.ofSeconds(1))
                .block();

        return response;
    }

   /* public StoreProductResponseDTO getProductWithFaultTolerance(Integer id) {
        // logic here to send get to product endpoint and retrieve product

        return new StoreProductResponseDTO(1, "Some", 15.99);
    }
    
    public StoreProductResponseDTO getProductWithoutFaultTolerance(Integer id) {
        // logic here to send get to product endpoint and retrieve product

        return new StoreProductResponseDTO(1, "Some", 15.99);
    }*/
    
    public UUID sellProductWithFaultTolerance(Integer id) {
        // logic here to send post to sell endpoint and retrieve uuid

        return UUID.randomUUID();
    }
    
    public UUID sellProductWithoutFaultTolerance(Integer id) {
        // logic here to send post to sell endpoint and retrieve uuid

        return UUID.randomUUID();
    }

}
