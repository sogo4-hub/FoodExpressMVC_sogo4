package es.daw.foodexpressmvc.controlador;

import es.daw.foodexpressmvc.dto.RestaurantDTO;
import es.daw.foodexpressmvc.service.RestauranteService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.security.Principal;
import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/restaurants")
public class RestaurantControler {
    private final RestauranteService restauranteService;
    //Pendiente de inyectar servicio
    @GetMapping("/menu")
    public String restaurantMenu(Model model, Principal principal){
        String username = principal.getName();
        model.addAttribute("username", username);
        return "restaurants/restaurants-menu";
    }

    @GetMapping
    public String listRestaurants(Model model){
        List<RestaurantDTO> restaurants = restauranteService.getRestaurants();
        model.addAttribute("restaurants", restaurants);
        return "restaurants/restaurants";
    }

    @GetMapping("/create")
    public String showCreateForm(Model model, Principal principal){
        String username = principal.getName();
        model.addAttribute("username", username);
        model.addAttribute("restaurant", new RestaurantDTO());
        return "restaurants/restaurant-create";
    }

    @PostMapping("/create")
    public String saveRestaurant(@ModelAttribute("restaurant")
                                     RestaurantDTO restaurantDTO,
                                     Principal principal,
                                     Model model){
        String username = principal.getName();
        model.addAttribute("username", username);
        RestaurantDTO saved = restauranteService.createRestaurant(restaurantDTO);
        model.addAttribute("restaurant", saved);
        //Pendiente pasar el restaurante y mostrarlo...se ha creado el restaurante...
        //Pendite:añadir la cabecera (username).....
        return "/restaurants/create-success";
    }
}
