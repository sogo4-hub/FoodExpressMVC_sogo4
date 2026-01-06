package es.daw.foodexpressmvc.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor; // Recomendado añadir este

@Data
@AllArgsConstructor
@NoArgsConstructor // Añádelo para evitar errores de deserialización JSON
@Builder
public class DishDTO {
    private String name;
    private double price;
    private String category;
    private String restaurantName;

    // --- NUEVOS CAMPOS ---
    private String restaurantAddress;
    private String restaurantPhone;
}