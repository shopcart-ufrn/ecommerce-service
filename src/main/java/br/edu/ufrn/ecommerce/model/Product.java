package br.edu.ufrn.ecommerce.model;

public class Product {
    
    private Integer id;
    private String name;
    private Double value;
    private Double valueBRL;

    public Product() {}

    public Product(Integer id, String name, Double value, Double valueBRL) {
        this.id = id;
        this.name = name;
        this.value = value;
        this.valueBRL = value;
    }

    public Integer getId() {
        return this.id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getValue() {
        return this.value;
    }

    public void setValue(Double value) {
        this.value = value;
    }

    public Double getValueBRL() {
        return this.valueBRL;
    }

    public void setValueBRL(Double valueBRL) {
        this.valueBRL = valueBRL;
    }

    public Integer getBonus() {
        return Math.toIntExact(Math.round(this.value));
    }

}
