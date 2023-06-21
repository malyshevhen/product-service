package ua.malysh.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ProductCreateForm {

        @NotNull
        @NotBlank
        @NotEmpty
        @Size(max = 45)
        private String name;

        @Size(max = 45)
        private String category;

        private Double nutritionalValueEnergie;
        private Double nutritionalValueCarbohydrates;
        private Double nutritionalValueFat;
        private Double nutritionalValueProtein;

        @Size(max = 255)
        private String description;

        @Size(max = 255)
        private String pictureUrl;
}
