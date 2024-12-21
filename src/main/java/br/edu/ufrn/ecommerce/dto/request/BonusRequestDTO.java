package br.edu.ufrn.ecommerce.dto.request;

public class BonusRequestDTO {

    private Integer user;
    private Integer bonus;

    public BonusRequestDTO() {}

    public BonusRequestDTO(Integer user, Integer bonus) {
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
