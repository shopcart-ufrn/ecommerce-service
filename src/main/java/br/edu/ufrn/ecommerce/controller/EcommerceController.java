package br.edu.ufrn.ecommerce.controller;

import br.edu.ufrn.ecommerce.dto.response.StoreProductResponseDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import br.edu.ufrn.ecommerce.dto.request.ProductRequestDTO;
import br.edu.ufrn.ecommerce.model.Product;
import br.edu.ufrn.ecommerce.service.EcommerceService;

import java.util.concurrent.TimeoutException;

@RestController
@RequestMapping("/buy")
public class EcommerceController {

    @Autowired
    private EcommerceService ecommerceService;

    @PostMapping
    public ResponseEntity<Long> buy(
        @RequestBody ProductRequestDTO productRequest
    ) {
        Long sellResponse;

        try {
            StoreProductResponseDTO productDto = ecommerceService.getProduct(productRequest.getProduct(), productRequest.getFt());

            Double valueBRL = ecommerceService.getExchangeToBRL(
                productDto.getValue(),
                productRequest.getFt()
            );

            Product product = new Product(productDto.getId(), productDto.getName(), productDto.getValue(), valueBRL);

            sellResponse = ecommerceService.sellProduct(productRequest.getUser(), productRequest.getFt());

            int roundedValue = (int) Math.round(productDto.getValue());

            ecommerceService.sendBonus(
                productRequest.getUser(),
                roundedValue,
                productRequest.getFt()
            );
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.FAILED_DEPENDENCY).build();
        }

        return ResponseEntity.status(HttpStatus.CREATED).body(sellResponse);
    }

    @GetMapping("/{id}")
    public void test(@PathVariable Integer id) {
        ecommerceService.getProduct(id, true);
    }
}
