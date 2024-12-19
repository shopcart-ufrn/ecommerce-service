package br.edu.ufrn.ecommerce.dto.response;

public class ExchangeResponseDTO {
    
    private Double usd;
    private String to;
    private Double rate;
    
    public ExchangeResponseDTO() {}

    public ExchangeResponseDTO(
        Double usd,
        String to,
        Double rate
    ) {
        this.usd = usd;
        this.to = to;
        this.rate = rate;
    }

    public Double getUsd() {
        return usd;
    }

    public String getTo() {
        return to;
    }

    public Double getRate() {
        return rate;
    }

}
