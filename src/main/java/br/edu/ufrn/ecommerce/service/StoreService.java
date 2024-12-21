package br.edu.ufrn.ecommerce.service;

import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import br.edu.ufrn.ecommerce.dto.request.StoreProductRequestDTO;
import br.edu.ufrn.ecommerce.dto.request.StoreSellRequestDTO;
import br.edu.ufrn.ecommerce.dto.response.ProductResponseDTO;
import br.edu.ufrn.ecommerce.dto.response.StoreProductResponseDTO;

@Service
public class StoreService {

    @Value("${store.baseUrl}")
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

    private StoreProductResponseDTO getProduct(StoreProductRequestDTO productRequestDTO) {
        // logic here to send request to /product endpoint
        
        return new StoreProductResponseDTO(1, "Some", 15.99);
    }

    private UUID postSell(StoreSellRequestDTO sellRequestDTO) {
        // logic here to send request to /sell endpoint

        return UUID.randomUUID();
    }

    public StoreProductResponseDTO getProductWithFaultTolerance(Integer id) {
        // logic here to send get to product endpoint and retrieve product

        return new StoreProductResponseDTO(1, "Some", 15.99);
    }
    
    public StoreProductResponseDTO getProductWithoutFaultTolerance(Integer id) {
        // logic here to send get to product endpoint and retrieve product

        return new StoreProductResponseDTO(1, "Some", 15.99);
    }
    
    public UUID sellProductWithFaultTolerance(Integer id) {
        // logic here to send post to sell endpoint and retrieve uuid

        return UUID.randomUUID();
    }
    
    public UUID sellProductWithoutFaultTolerance(Integer id) {
        // logic here to send post to sell endpoint and retrieve uuid

        return UUID.randomUUID();
    }
}
