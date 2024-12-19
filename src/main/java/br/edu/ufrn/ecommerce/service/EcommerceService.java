package br.edu.ufrn.ecommerce.service;

import java.io.IOException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.edu.ufrn.ecommerce.dto.BonusRequestDTO;

@Service
public class EcommerceService {
    
    @Autowired
    private StoreService storeService;

    @Autowired
    private ExchangeService exchangeService;
    
    @Autowired
    private FidelityService fidelityService;

    private static final Logger logger = LoggerFactory.getLogger(EcommerceService.class);
    
    public void processBonus(BonusRequestDTO bonusRequestDTO) throws IOException, InterruptedException {
        fidelityService.applyBonus(bonusRequestDTO);
    }

    public Double getExchangeToBRL(Double valueUSD, Boolean ft) {
        Double rate;

        if (ft) {
            rate = exchangeService.getExchangeRateWithFaultTolerance();
        } else {
            rate = exchangeService.getExchangeRateWithoutFaultTolerance();
        }

        Double valueBRL = valueUSD * rate;
    
        return valueBRL;
    }

}
