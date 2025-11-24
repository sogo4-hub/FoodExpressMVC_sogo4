package es.daw.foodexpressmvc.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
@Builder
public class DishDTO {
    private String name;
    private double price;
    private String category;
    private String restaurantName;
}
