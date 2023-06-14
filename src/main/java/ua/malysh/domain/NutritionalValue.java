package ua.malysh.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.Data;

@Embeddable
@Data
public class NutritionalValue {

    @Column(name = "energie_value",
            nullable = false)
    private Double energie;

    @Column(name = "carbohydrates_value",
            nullable = false)
    private Double carbohydrates;

    @Column(name = "fat_value",
            nullable = false)
    private Double fat;

    @Column(name = "protein_value",
            nullable = false)
    private Double protein;
}
