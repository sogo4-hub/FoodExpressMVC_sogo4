package es.daw.foodexpressmvc.controlador;

import es.daw.foodexpressmvc.dto.OrderDTO;
import es.daw.foodexpressmvc.exception.ConnectApiRestException;
import es.daw.foodexpressmvc.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import es.daw.foodexpressmvc.service.RestauranteService;
import es.daw.foodexpressmvc.service.UserService;
import java.util.Arrays;
import java.util.List;
import java.security.Principal;

@Controller
@RequestMapping("/orders")
@RequiredArgsConstructor
public class OrderController {
    private final OrderService orderService;
    private final UserService userService; // Inyectar nuevo servicio
    private final RestauranteService restauranteService; // Inyectar servicio existente

    @GetMapping
    public String listOrders(
            @RequestParam(required = false) String status,
            @RequestParam(required = false) Long userId,
            @RequestParam(required = false) Long restaurantId,
            @RequestParam(defaultValue = "id") String sortBy,     // Nuevo
            @RequestParam(defaultValue = "ASC") String direction, // Nuevo
            Model model, Principal principal) {

        try {
            // Añadimos el nombre del usuario al modelo
            if (principal != null) {
                model.addAttribute("username", principal.getName());
            }
            // 1. Cargar datos para los filtros
            model.addAttribute("allStatuses", Arrays.asList("PENDIENTE", "PREPARANDO", "EN_CAMINO", "ENTREGADO", "CANCELADO"));
            model.addAttribute("allUsers", userService.getAllUsersFromApi());
            model.addAttribute("allRestaurants", restauranteService.getRestaurants());

            // 2. Obtener pedidos filtrados
            List<OrderDTO> orders = orderService.getFilteredOrders(status, userId, restaurantId, sortBy, direction);
            model.addAttribute("orders", orders);

            //Pasamos la configuración actual a la vista para mantener el estado de los botones
            model.addAttribute("currentSortBy", sortBy);
            model.addAttribute("currentDirection", direction);

            return "orders-list";
        } catch (ConnectApiRestException e) {
            model.addAttribute("errorMessage", e.getMessage());
            return "api-error";
        }
    }
}
