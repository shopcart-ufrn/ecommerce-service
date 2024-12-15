package br.edu.ufrn.ecommerce.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EcommerceService {
    
    @Autowired
    ExchangeService exchangeService;
    
    @Autowired
    FidelityService fidelityService;
    
    @Autowired
    StoreService storeService;
    
}
