package es.daw.foodexpressmvc.controlador;

import es.daw.foodexpressmvc.dto.RestaurantDTO;
import es.daw.foodexpressmvc.exception.ConnectApiRestException;
import es.daw.foodexpressmvc.service.RestauranteService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/restaurants")
public class RestaurantControler {
    private final RestauranteService restauranteService;
    //Pendiente de inyectar servicio
//    @GetMapping("/menu")
//    public String restaurantMenu(Model model){
//        return "restaurants/restaurants-menu";
//    }

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
        return "restaurants/restaurant-form";
    }

    @PostMapping("/create")
    public String saveRestaurant(@Valid @ModelAttribute("restaurant")
                                     RestaurantDTO restaurantDTO,
                                     BindingResult result,
                                     Principal principal,
                                     Model model){
        String username = principal.getName();
        model.addAttribute("username", username);

        // 1. Validación Local (rápida)
        if (result.hasErrors()) {
            model.addAttribute("username", principal.getName());
            // No llamamos a la API, volvemos al formulario y Thymeleaf muestra los errores en los campos
            model.addAttribute("restaurant", restaurantDTO);
            return "restaurants/restaurant-form";
        }

        try {
            RestaurantDTO saved = restauranteService.createRestaurant(restaurantDTO);
            model.addAttribute("restaurant", saved);
            model.addAttribute("username", principal.getName());
            return "/restaurants/create-success";
        } catch (Exception e) {
            throw new ConnectApiRestException(e.getMessage());
        }
    }

    @PostMapping("/delete/{id}")
    public String deleteRestaurant(@PathVariable Long id){
        restauranteService.delete(id);
        return "redirect:/restaurants"; //llamar al endpoint
    }

    @GetMapping("/edit/{id}")
    public String showRestaurant(@PathVariable("id") Long id,Model model){
        RestaurantDTO restaurantDTO = restauranteService.getRestaurantById(id);
        model.addAttribute("restaurant", restaurantDTO);
        return "restaurants/restaurant-form";
    }

    @PostMapping("/update/{id}")
    public String updateRestaurant(@PathVariable Long id,
                                   @Valid @ModelAttribute("restaurant") RestaurantDTO restaurantDTO,
                                   BindingResult result,
                                   Model model){
        if (result.hasErrors()) {
            model.addAttribute("restaurant", restaurantDTO);
            return "restaurants/restaurant-form";
        }
        RestaurantDTO updated = restauranteService.updateRestaurant(id, restaurantDTO);
        model.addAttribute("restaurant", updated);
        return "redirect:/restaurants";
    }

}
