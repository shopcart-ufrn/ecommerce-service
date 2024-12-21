package br.edu.ufrn.ecommerce.dto.response;

public class StoreProductResponseDTO {
    
    private Integer id;
    private String name;
    private Double value;
    
    public StoreProductResponseDTO() {}

    public StoreProductResponseDTO(
        Integer id,
        String name,
        Double value
    ) {
        this.id = id;
        this.name = name;
        this.value = value;
    }

    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Double getValue() {
        return value;
    }

}
