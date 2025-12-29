package es.daw.foodexpressmvc.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;


@Data
public class RestaurantDTO {
    private Long id;
    private String name;
    private String address;
    // ===== PHONE =====
    @NotBlank(message = "Phone is required")
    @Size(min = 9, max = 20, message = "Phone must be between 9 and 20 digits")
    // Admite: números, espacios, guiones, + (por si es internacional)
    @Pattern(
            regexp = "^[0-9 +()-]{9,20}$",
            message = "Invalid phone format"
    )
    private String phone;


}
