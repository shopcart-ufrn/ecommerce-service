package br.edu.ufrn.ecommerce.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.edu.ufrn.ecommerce.dto.request.ProductRequestDTO;
import br.edu.ufrn.ecommerce.model.Product;
import br.edu.ufrn.ecommerce.model.User;
import br.edu.ufrn.ecommerce.service.EcommerceService;

@RestController
@RequestMapping("/buy")
public class EcommerceController {

    @Autowired
    private EcommerceService ecommerceService;

    @PostMapping
    public ResponseEntity<Void> buy(
        @RequestBody ProductRequestDTO productRequest
    ) {

        Product product = new Product();
        product.setId(productRequest.getProduct());
        
        User user = new User();
        user.setId(productRequest.getUser());

        // ProductResponseDTO productResponse = ecommerceService.getProduct(productRequest.product);
        product.setName("Shoes");
        product.setValue(Double.valueOf(45.89));
        user.setBonus(product.getBonus());

        Double valueBRL = ecommerceService.getExchangeToBRL(
            product.getValue(),
            productRequest.getFt()
        );
        product.setValueBRL(valueBRL);

        // SellResponseDTO sellResponse ecommerceService.sellProduct(productRequest.product);

        // ecommerceService.sendBonus(
        //     user.getId(),
        //     user.getBonus(),
        //     productRequest.getFt()
        // );

        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
