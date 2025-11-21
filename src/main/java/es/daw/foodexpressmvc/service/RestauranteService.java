package es.daw.foodexpressmvc.service;

import es.daw.foodexpressmvc.dto.RestaurantDTO;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RestauranteService {

    public List<RestaurantDTO> getRestaurants(){
        //Pendiente de usar WebClient para hacer la peticion de endpoint GET
        //http://localhost:8080/api/restaurants
        //Obtendremos un json con la lista de restaurantes
        //Debemos convertir el json en una lista de RestaurantDTO
        return null;
    }
}
