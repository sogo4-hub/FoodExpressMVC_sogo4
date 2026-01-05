package es.daw.foodexpressmvc.controlador;

import es.daw.foodexpressmvc.dto.DishDTO;
import es.daw.foodexpressmvc.service.DishService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.security.Principal;
import java.util.List;
import es.daw.foodexpressmvc.dto.PageResponse;
import org.springframework.web.bind.annotation.RequestParam;


@Controller
@RequiredArgsConstructor
public class DishController {
    private final DishService dishService;
    //Pendiente de inyectar servicio
    @GetMapping("/dishes")
    public String listDishes(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size,
            Model model,
            Principal principal) {
        if (principal != null) {
            model.addAttribute("username", principal.getName());
        }
        // Llamamos al servicio paginado
        PageResponse<DishDTO> dishPage = dishService.getDishes(page, size);
        // Pasamos al modelo la página completa (para la navegación)
        model.addAttribute("page", dishPage);
        // Y la lista de platos (para la tabla)
        model.addAttribute("dishes", dishPage.getContent());

        return "dishes/dishes";
    }
}
