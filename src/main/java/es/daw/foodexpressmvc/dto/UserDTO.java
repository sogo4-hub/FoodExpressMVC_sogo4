// Opcional: Actualizar UserDTO en el MVC para incluir más info si quieres mostrarla
package es.daw.foodexpressmvc.dto;

import lombok.Data;

@Data
public class UserDTO {
    private Long id;
    private String username;
}
