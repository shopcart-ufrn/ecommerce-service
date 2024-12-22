package br.edu.ufrn.ecommerce.dto.response;

import java.util.UUID;

public class StoreSellResponseDTO {
    
    private UUID id;
    
    public StoreSellResponseDTO() {}

    public StoreSellResponseDTO(
        UUID id
    ) {
        this.id = id;
    }

    public UUID getId() {
        return id;
    }

}
