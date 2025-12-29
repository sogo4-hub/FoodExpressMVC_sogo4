package es.daw.foodexpressmvc.service;

import es.daw.foodexpressmvc.dto.RestaurantDTO;
import es.daw.foodexpressmvc.exception.ConnectApiException;
import es.daw.foodexpressmvc.exception.ConnectApiRestException;
import lombok.RequiredArgsConstructor;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.Arrays;
import java.util.List;



@Service
@RequiredArgsConstructor
public class RestauranteService {
    private final WebClient webClientAPI;
    private final ApiAuthService apiAuthService;


    public List<RestaurantDTO> getRestaurants(){
        RestaurantDTO[] restaurantes;
        try {
            restaurantes = webClientAPI
                    .get()
                    .uri("/restaurants")
                    .retrieve()//verifica el status. Si 4xx o 5xx, lanza error. Si es 2xx continua
                    .bodyToMono(RestaurantDTO[].class)
                    .block();//Bloquea y espera. Sincrono
        }catch (Exception e){
            throw new ConnectApiException("Could not connect to foodEspress API", e);
        }
        //return List.of(restaurantes);//La diferencia entre List.of y Arrays.asList es que List.of es inmutable
        return Arrays.asList(restaurantes);
    }

    /**
     * PENDIENTE CREAR ENVIANDO EL TOKEN. Necesita el token para crear un restaurante jwt
     * @param restaurantDTO
     * @return
     */
    public RestaurantDTO createRestaurant(RestaurantDTO restaurantDTO){
        //PENDIENTE CREAR ENVIANDO EL TOKEN
        RestaurantDTO dto;
        String token = apiAuthService.getToken();
        try {
            dto = webClientAPI
                    .post()
                    .uri("/restaurants")
                    .header("Authorization", "Bearer " + token)
                    .bodyValue(restaurantDTO)
                    .retrieve()//verifica el status. Si 4xx o 5xx, lanza error. Si es 2xx continua
                    .bodyToMono(RestaurantDTO.class)
                    .block();//Bloquea y espera. Sincrono
        }catch (Exception e){
//            throw new ConnectApiException("Could not connect to foodEspress API", e);
            throw new ConnectApiRestException(e.getMessage());
        }
        //return List.of(restaurantes);//La diferencia entre List.of y Arrays.asList es que List.of es inmutable
        //Pendiente usar Optional
        return dto;
    }

    public void delete(Long id){
        String token = apiAuthService.getToken();
        try {
            webClientAPI
                    .delete()
                    .uri("/restaurants/{id}", id)
                    .header("Authorization", "Bearer " + token)
                    .retrieve()
                    .bodyToMono(Void.class)
                    .block();//Bloquea y espera. Sincrono
        }catch (Exception e){
            throw new ConnectApiRestException(e.getMessage());
        }
    }

//    public RestaurantDTO getRestaurantById(Long id) {
//        String token = apiAuthService.getToken();
//        RestaurantDTO dto;
//
//        try {
//            dto = webClientAPI
//                    .get()
//                    .uri("/restaurants/{id}", id)
//                    .header("Authorization", "Bearer " + token)
//                    .retrieve()
//                    .bodyToMono(RestaurantDTO.class)
//                    .block();
//        }catch (Exception e){
//            throw new ConnectApiRestException(e.getMessage());
//        }
//        return dto;
//    }

    public RestaurantDTO updateRestaurant(Long id, RestaurantDTO restaurantDTO){
        String token = apiAuthService.getToken();
        RestaurantDTO dto;
        try {
            dto = webClientAPI
                    .put()
                    .uri("/restaurants/{id}", id)
                    .header("Authorization", "Bearer " + token)
                    .bodyValue(restaurantDTO)
                    .retrieve()
                    .bodyToMono(RestaurantDTO.class)
                    .block();
        }catch (Exception e){
            throw new ConnectApiRestException(e.getMessage());
        }
        return dto;
    }

    public RestaurantDTO getRestaurantById(Long id) {
        return getRestaurants().stream()
                .filter(restaurant -> restaurant.getId().equals(id))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("No existe el restaurante "+id));
    }

}

