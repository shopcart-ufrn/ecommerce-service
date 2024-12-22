package br.edu.ufrn.ecommerce.service;

import java.time.Duration;
import java.util.UUID;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;

import br.edu.ufrn.ecommerce.dto.request.StoreSellRequestDTO;
import br.edu.ufrn.ecommerce.dto.response.StoreProductResponseDTO;

@Service
public class StoreService {

    @Value("${store.baseUrl}")
    private  String baseUrl;

    private  WebClient client;

    private static final Logger logger = LoggerFactory.getLogger(StoreService.class);

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

    private Long postSell(StoreSellRequestDTO sellRequestDTO) {
        String endpoint = "/product/sell";
        WebClient client = this.getClient();
        Long response = client
                .post()
                .uri(endpoint)
                .body(BodyInserters.fromValue(sellRequestDTO))
                .retrieve()
                .bodyToMono(Long.class)
                .timeout(Duration.ofSeconds(1))
                .block();

        return response;
    }

    public StoreProductResponseDTO getProductWithFaultTolerance(Integer id) {
        StoreProductResponseDTO product = getProduct(id);

        return product;
    }

    public StoreProductResponseDTO getProductWithoutFaultTolerance(Integer id) {
        StoreProductResponseDTO product = getProduct(id);

        return product;
    }

    @CircuitBreaker(name = "storeService", fallbackMethod = "sellProductFallBack")
    public Long sellProductWithFaultTolerance(Integer id) {
        Long result = postSell(new StoreSellRequestDTO(id));
        return result;
    }
    
    public Long sellProductWithoutFaultTolerance(Integer id) {
        Long result = postSell(new StoreSellRequestDTO(id));
        return result;
    }

    public Long sellProductFallBack(Integer id, Throwable throwable) {
        throw new RuntimeException("The circuit breaker has been triggered", throwable);
    }

}
