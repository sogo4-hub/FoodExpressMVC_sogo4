package es.daw.foodexpressmvc.controlador;

import es.daw.foodexpressmvc.dto.DishDTO;
import es.daw.foodexpressmvc.dto.RestaurantDTO;
import es.daw.foodexpressmvc.service.DishService;
import es.daw.foodexpressmvc.service.RestauranteService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class DishController {
    private final DishService dishService;
    //Pendiente de inyectar servicio
    @GetMapping("/dishes")
    public String listDishes(Model model){
        List<DishDTO> dishes = dishService.getDishes();
        model.addAttribute("dishes", dishes);
        return "dishes";
    }
}
