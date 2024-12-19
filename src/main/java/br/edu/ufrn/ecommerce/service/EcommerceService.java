package br.edu.ufrn.ecommerce.service;

import br.edu.ufrn.ecommerce.dto.BonusRequestDTO;
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

    public void processBonus(BonusRequestDTO bonusRequestDTO) throws IOException, InterruptedException {
        fidelityService.applyBonus(bonusRequestDTO);
    }
}
