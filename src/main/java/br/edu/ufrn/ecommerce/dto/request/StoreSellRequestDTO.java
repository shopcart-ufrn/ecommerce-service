package br.edu.ufrn.ecommerce.dto.request;

public class StoreSellRequestDTO {
    
    private Integer product;

    public StoreSellRequestDTO() {}

    public StoreSellRequestDTO(
        Integer product
    ) {
        this.product = product;
    }

    public Integer getProduct() {
        return product;
    }

}
