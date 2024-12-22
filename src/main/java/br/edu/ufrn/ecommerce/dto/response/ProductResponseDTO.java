package br.edu.ufrn.ecommerce.dto.response;

import java.util.UUID;

public class ProductResponseDTO {
    
    private UUID id;
    
    public ProductResponseDTO() {}

    public ProductResponseDTO(
        UUID id
    ) {
        this.id = id;
    }

    public UUID getId() {
        return id;
    }

}
