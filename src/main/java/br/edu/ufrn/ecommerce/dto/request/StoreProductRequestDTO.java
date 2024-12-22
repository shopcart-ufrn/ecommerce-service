package br.edu.ufrn.ecommerce.dto.request;

public class StoreProductRequestDTO {
    
    private Integer product;

    public StoreProductRequestDTO() {}

    public StoreProductRequestDTO(
        Integer product
    ) {
        this.product = product;
    }

    public Integer getProduct() {
        return product;
    }

}
