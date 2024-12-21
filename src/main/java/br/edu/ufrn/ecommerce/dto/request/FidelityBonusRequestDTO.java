package br.edu.ufrn.ecommerce.dto.request;

public class FidelityBonusRequestDTO {

    private Integer user;
    private Integer bonus;

    public FidelityBonusRequestDTO() {}

    public FidelityBonusRequestDTO(Integer user, Integer bonus) {
        this.user = user;
        this.bonus = bonus;
    }

    public Integer getUser() {
        return user;
    }

    public Integer getBonus() {
        return bonus;
    }
}
