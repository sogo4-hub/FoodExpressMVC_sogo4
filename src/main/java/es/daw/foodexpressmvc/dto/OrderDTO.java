package es.daw.foodexpressmvc.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrderDTO {

    private Long id;              // ID único del pedido
    private String orderDate;     // Fecha ya formateada como String desde la API ("dd/MM/yyyy HH:mm")
    private String status;        // Estado del pedido (CREADO, ENTREGADO, etc.)

    // Información del Usuario
    private Long userId;          // ID del cliente
    private String username;      // Nombre de usuario para mostrar en la tabla

    // Información del Restaurante
    private Long restaurantId;    // ID del restaurante
    private String restaurantName; // Nombre del restaurante para mostrar en la tabla
}