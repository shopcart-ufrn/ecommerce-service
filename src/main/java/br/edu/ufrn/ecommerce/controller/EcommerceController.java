package br.edu.ufrn.ecommerce.controller;

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
    ) throws IOException, InterruptedException {
        var prod = ecommerceService.getProd(product.getUser());
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
