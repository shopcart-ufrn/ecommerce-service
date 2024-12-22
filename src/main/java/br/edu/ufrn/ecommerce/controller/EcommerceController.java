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
    public ResponseEntity<Void> buy(
        @RequestBody ProductRequestDTO productRequest
    ) throws TimeoutException {

        StoreProductResponseDTO productDto = ecommerceService.getProduct(productRequest.getProduct(), productRequest.getFt());

       Double valueBRL = ecommerceService.getExchangeToBRL(
            productDto.getValue(),
            productRequest.getFt()
        );

        Product product = new Product(productDto.getId(), productDto.getName(), productDto.getValue(), valueBRL);

        Long sellResponse = ecommerceService.sellProduct(productRequest.getUser(), productRequest.getFt());

        int roundedValue = (int) Math.round(productDto.getValue());

        ecommerceService.sendBonus(
            productRequest.getUser(),
            roundedValue,
            productRequest.getFt()
        );

        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @GetMapping("/{id}")
    public void test(@PathVariable Integer id) {
        ecommerceService.getProduct(id, true);
    }
}
