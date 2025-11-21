package es.daw.foodexpressmvc.controlador;

import es.daw.foodexpressmvc.dto.RestaurantDTO;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

@Controller
public class RestaurantControler {

    //Pendiente de inyectar servicio
    @GetMapping("/restaurants")
    public String listRestaurants(Model model){
        //Pendiente obtener del servicio la lista de restaurantes

        List<RestaurantDTO> restaurants = new ArrayList<>();
        model.addAttribute("restaurants", restaurants);
        return "restaurants";
    }
}
