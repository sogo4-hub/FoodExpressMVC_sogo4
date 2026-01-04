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
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

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
        return "restaurants/restaurants"; //subcarpeta restaurants y dentro plantilla restaurants.html
    }

    @GetMapping("/create")
    public String showCreateForm(Model model, Principal principal){
        String username = principal.getName();
        model.addAttribute("username", username);
        model.addAttribute("restaurant", new RestaurantDTO());
        model.addAttribute("mode", "create"); // <-- Indicamos que es creación
        return "restaurants/restaurant-form";
    }

    @PostMapping("/create")
    public String saveRestaurant(@Valid @ModelAttribute("restaurant") RestaurantDTO restaurantDTO,
                                 BindingResult result, // <--- DEBE IR AQUÍ (Pegado al DTO)
                                 Model model,           // <--- El model puede ir después
                                 RedirectAttributes redirectAttributes) {// 1. Inyectamos RedirectAttributes

        // 3. Comprobamos si hay errores de validación (como el teléfono vacío)
        if (result.hasErrors()) {
            model.addAttribute("mode", "create"); // Volvemos en modo creación
            return "restaurants/restaurant-form"; // Devolvemos la vista del formulario
        }


        try {
            // Llamada al servicio para crear el restaurante en la API
            RestaurantDTO saved = restauranteService.createRestaurant(restaurantDTO);
            // 2. Guardamos el objeto en "FlashAttributes" (un almacén temporal en la sesión)
            //PRG (post + redirect + get)....fallo de F5
            redirectAttributes.addFlashAttribute("restaurant", saved);
            redirectAttributes.addFlashAttribute("success", true);

            // 3. ¡IMPORTANTE! Devolvemos una redirección (orden 302), no una vista
            return "redirect:/restaurants/success";// endpoint, no una plantilla!!! Lo puedo diseñar en el controlador o en MvcConfig
        } catch (Exception e) {
            throw new ConnectApiRestException(e.getMessage());
        }
    }

//    @GetMapping("/success")
//    public String showSuccessPage() {
//        // Este método simplemente muestra la página.
//        // Los datos que pusimos en FlashAttributes ya estarán disponibles en el HTML automáticamente.
//        return "restaurants/create-success";
//    }

    @PostMapping("/delete/{id}")
    public String deleteRestaurant(@PathVariable Long id){
        restauranteService.delete(id);
        return "redirect:/restaurants"; //llamar al endpoint
    }

    @GetMapping("/edit/{id}")
    public String showRestaurant(@PathVariable("id") Long id,Model model){
        RestaurantDTO restaurantDTO = restauranteService.getRestaurantById(id);
        model.addAttribute("restaurant", restaurantDTO);
        model.addAttribute("mode", "update"); // <-- Indicamos que es edición
        return "restaurants/restaurant-form";
    }

    @PostMapping("/update/{id}")
    public String updateRestaurant(@PathVariable Long id,
                                   @Valid @ModelAttribute("restaurant") RestaurantDTO restaurantDTO,
                                   BindingResult result,
                                   Model model){
        if (result.hasErrors()) {
            model.addAttribute("restaurant", restaurantDTO);
            model.addAttribute("mode", "update"); // <-- Mantener el modo si fall
            return "restaurants/restaurant-form";
        }
        RestaurantDTO updated = restauranteService.updateRestaurant(id, restaurantDTO);
        model.addAttribute("restaurant", updated);
        return "redirect:/restaurants";
    }

}
