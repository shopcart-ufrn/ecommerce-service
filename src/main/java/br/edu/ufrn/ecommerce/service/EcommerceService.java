package br.edu.ufrn.ecommerce.service;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.JsonNode;

import br.edu.ufrn.ecommerce.dto.BonusRequestDTO;

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
    
    public void processBonus(BonusRequestDTO bonusRequestDTO) throws IOException, InterruptedException {
        fidelityService.applyBonus(bonusRequestDTO);
    }
}
