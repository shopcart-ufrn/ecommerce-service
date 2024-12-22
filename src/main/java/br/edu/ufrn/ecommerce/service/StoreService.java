package br.edu.ufrn.ecommerce.service;

import java.time.Duration;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;

import io.github.resilience4j.ratelimiter.annotation.RateLimiter;
import io.github.resilience4j.retry.annotation.Retry;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;

import br.edu.ufrn.ecommerce.dto.request.StoreSellRequestDTO;
import br.edu.ufrn.ecommerce.dto.response.StoreProductResponseDTO;
import org.springframework.web.server.ResponseStatusException;

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

    // @RateLimiter(name = "storeServiceRateLimiter", fallbackMethod = "rateLimiterFallback")
    @Retry(name = "storeService", fallbackMethod = "getProductFallBack")
    public StoreProductResponseDTO getProductWithFaultTolerance(Integer id) {
        return getProduct(id);
    }

    public StoreProductResponseDTO rateLimiterFallback(Integer id, Throwable throwable) {
        throw new ResponseStatusException(HttpStatus.TOO_MANY_REQUESTS, "Rate limit exceeded");
    }

    public Long getProductFallBack(Integer id, Throwable throwable) {
        throw new RuntimeException("The fallback of getProduct has been triggered", throwable);
    }

    public StoreProductResponseDTO getProductWithoutFaultTolerance(Integer id) {
        return getProduct(id);
    }

    @Retry(name = "storeService", fallbackMethod = "sellProductFallBack")
    public Long sellProductWithFaultTolerance(Integer id) {
        Long result = postSell(new StoreSellRequestDTO(id));
        return result;
    }
    
    public Long sellProductWithoutFaultTolerance(Integer id) {
        Long result = postSell(new StoreSellRequestDTO(id));
        return result;
    }

    public Long sellProductFallBack(Integer id, Throwable throwable) {
        throw new RuntimeException("The  fallback of sellProduct has been triggered", throwable);
    }
}
