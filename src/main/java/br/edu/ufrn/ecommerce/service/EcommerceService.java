package br.edu.ufrn.ecommerce.service;

import java.util.UUID;
import java.util.concurrent.TimeoutException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.edu.ufrn.ecommerce.dto.response.StoreProductResponseDTO;

@Service
public class EcommerceService {
    
    @Autowired
    private StoreService storeService;

    @Autowired
    private ExchangeService exchangeService;
    
    @Autowired
    private FidelityService fidelityService;

    private static final Logger logger = LoggerFactory.getLogger(EcommerceService.class);

    public StoreProductResponseDTO getProduct(Integer id, Boolean ft) {
        StoreProductResponseDTO product;

       /* if (ft) {
            product = storeService.getProductWithFaultTolerance(id);
        } else {
            product = storeService.getProductWithoutFaultTolerance(id);
        };*/

        product = storeService.getProduct(id);
    
        return product;
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

    public UUID sellProduct(Integer id, Boolean ft) {
        UUID sale;

        if (ft) {
            sale = storeService.sellProductWithFaultTolerance(id);
        } else {
            sale = storeService.sellProductWithoutFaultTolerance(id);
        }
    
        return sale;
    }

    public void sendBonus(Integer user, Integer bonus, Boolean ft) throws TimeoutException {
        if (ft) {
            fidelityService.sendBonusWithFaultTolerance(user, bonus);
        } else {
            fidelityService.sendBonusWithoutFaultTolerance(user, bonus);
        }
    }

}
