package ua.malysh.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.Data;

@Embeddable
@Data
public class NutritionalValue {

    @Column(name = "carbohydrates_value")
    private Double carbohydrates;

    @Column(name = "fat_value")
    private Double fat;

    @Column(name = "protein_value")
    private Double protein;    
}
