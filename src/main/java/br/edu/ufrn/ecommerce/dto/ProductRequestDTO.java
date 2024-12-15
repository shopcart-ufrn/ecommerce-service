package br.edu.ufrn.ecommerce.dto;

public class ProductRequestDTO {
    
    private Integer product;
    private Integer user;
    private Boolean ft;

    public ProductRequestDTO() {}

    public ProductRequestDTO(Integer product, Integer user, Boolean ft) {
        this.product = product;
        this.user = user;
        this.ft = ft;
    }

    public Integer getProduct() {
        return product;
    }

    public Integer getUser() {
        return user;
    }

    public Boolean getFt() {
        return ft;
    }

}
