package es.daw.foodexpressmvc.service;

import es.daw.foodexpressmvc.dto.DishDTO;
import es.daw.foodexpressmvc.dto.RestaurantDTO;
import es.daw.foodexpressmvc.exception.ConnectApiException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.Arrays;
import java.util.List;
@Service
@RequiredArgsConstructor
public class DishService {
    private final WebClient webClientAPI;


    public List<DishDTO> getDishes(){
        DishDTO[] dishes;
        try {
            dishes = webClientAPI
                    .get()
                    .uri("/dish")
                    .retrieve()//verifica el status. Si 4xx o 5xx, lanza error. Si es 2xx continua
                    .bodyToMono(DishDTO[].class)
                    .block();//Bloquea y espera. Sincrono
        }catch (Exception e){
            throw new ConnectApiException("Could not connect to foodEspress API", e);
        }
        //return List.of(restaurantes);//La diferencia entre List.of y Arrays.asList es que List.of es inmutable
        return Arrays.asList(dishes);
    }
}
