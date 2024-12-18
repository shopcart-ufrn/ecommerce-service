package br.edu.ufrn.ecommerce.service;

import com.fasterxml.jackson.databind.JsonNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class EcommerceService {
    
    @Autowired
    ExchangeService exchangeService;
    
    @Autowired
    FidelityService fidelityService;
    
    @Autowired
    StoreService storeService;

    public JsonNode getProd(Integer id) throws IOException, InterruptedException {
        return storeService.get(id);
    }
    
}
