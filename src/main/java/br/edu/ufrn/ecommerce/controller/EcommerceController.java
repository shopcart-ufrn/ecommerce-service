package br.edu.ufrn.ecommerce.controller;

import br.edu.ufrn.ecommerce.dto.BonusRequestDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.edu.ufrn.ecommerce.dto.ProductRequestDTO;
import br.edu.ufrn.ecommerce.service.EcommerceService;

import java.io.IOException;

@RestController
@RequestMapping("/buy")
public class EcommerceController {

    @Autowired
    private EcommerceService ecommerceService;

    @PostMapping
    public ResponseEntity<Void> buy(
        @RequestBody ProductRequestDTO product
    ) {
        
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @PostMapping("/bonus")
    public ResponseEntity<Void> bonus(@RequestBody BonusRequestDTO bonusRequestDTO) throws IOException, InterruptedException {
        ecommerceService.processBonus(bonusRequestDTO);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
