package es.daw.foodexpressmvc.service;

import es.daw.foodexpressmvc.dto.DishDTO;
import es.daw.foodexpressmvc.dto.RestaurantDTO;
import es.daw.foodexpressmvc.exception.ConnectApiException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import java.util.Arrays;
import java.util.List;
import es.daw.foodexpressmvc.dto.PageResponse;
import org.springframework.core.ParameterizedTypeReference;


@Service
@RequiredArgsConstructor
public class DishService {
    private final WebClient webClientAPI;


    // Cambiamos el retorno a PageResponse<DishDTO> y añadimos params page y size
    public PageResponse<DishDTO> getDishes(int page, int size) {
        try {
            return webClientAPI
                    .get()
                    // Construimos la URI con parámetros: /api/dish?page=0&size=5
                    .uri(uriBuilder -> uriBuilder
                            .path("/dish")
                            .queryParam("page", page)
                            .queryParam("size", size)
                            .build())
                    .retrieve()
                    // Usamos ParameterizedTypeReference para mapear el genérico <DishDTO>
                    .bodyToMono(new ParameterizedTypeReference<PageResponse<DishDTO>>() {})
                    .block();
        } catch (Exception e) {
            throw new ConnectApiException("Error al conectar con la API de platos", e);
        }
    }
}
