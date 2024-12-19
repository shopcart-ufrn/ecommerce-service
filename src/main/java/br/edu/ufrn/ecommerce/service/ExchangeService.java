package br.edu.ufrn.ecommerce.service;

import java.time.Duration;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;

import br.edu.ufrn.ecommerce.dto.response.ExchangeResponseDTO;
import br.edu.ufrn.ecommerce.exception.ExchangeRateUnavailable;

@Service
public class ExchangeService {

    @Value("${exchange.baseUrl}")
    private String baseUrl;

    private WebClient client;

    private static final Logger logger = LoggerFactory.getLogger(ExchangeService.class);

    private WebClient getClient() {
        if (this.client == null) {
            this.client = WebClient.builder()
            .baseUrl(this.baseUrl)
            .build();
        }

        return this.client;
    }

    private Double getExchangeRate() {
        String endpoint = "/exchange";

        WebClient client = this.getClient();

        ExchangeResponseDTO response = client
            .get()
            .uri(endpoint)
            .retrieve()
            .bodyToMono(ExchangeResponseDTO.class)
            .timeout(Duration.ofSeconds(1))
            .block();
        
        logger.debug(
            String.format(
                "Exchange successfully retrieved from %s: %s",
                this.baseUrl + endpoint, response.toString()
            )
        );

        Double rate = response.getRate();

        return rate;
    }

    @CircuitBreaker(name = "exchangeService", fallbackMethod = "getExchangeRateFallback")
    @CachePut(value = "exchangeCache", key="'lastExchangeRate'")
    public Double getExchangeRateWithFaultTolerance() {
        Double rate = this.getExchangeRate();

        logger.debug("Exchange successfully retrieved with ft enabled.");

        return rate;
    }

    public Double getExchangeRateWithoutFaultTolerance() {
        Double rate = this.getExchangeRate();

        logger.debug("Exchange successfully retrieved with ft disabled.");

        return rate;
    }

    @Cacheable(value = "exchangeCache", key = "'lastExchangeRate'")
    public Double getExchangeRateFallback(Throwable t) throws ExchangeRateUnavailable {
        String message = "Fallback to cached value raised an exception: " + t.getMessage();
    
        throw new ExchangeRateUnavailable(message);
    }

}
