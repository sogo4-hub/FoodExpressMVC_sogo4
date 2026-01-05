package es.daw.foodexpressmvc.service;

import es.daw.foodexpressmvc.dto.DishDTO;
import es.daw.foodexpressmvc.dto.ErrorDTO; // DTO para capturar el error de la API
import es.daw.foodexpressmvc.dto.PageResponse;
import es.daw.foodexpressmvc.exception.ConnectApiRestException; // Excepción personalizada
import lombok.RequiredArgsConstructor;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class DishService {
    private final WebClient webClientAPI;

    public PageResponse<DishDTO> getDishes(int page, int size) {
        return webClientAPI
                .get()
                .uri(uriBuilder -> uriBuilder
                        .path("/dish")
                        .queryParam("page", page)
                        .queryParam("size", size)
                        .build())
                .retrieve()
                // Implementación del onStatus siguiendo tu patrón solicitado
                .onStatus(
                        HttpStatusCode::isError, // Captura errores 4xx y 5xx
                        clientResponse -> clientResponse.bodyToMono(ErrorDTO.class)
                                .defaultIfEmpty(new ErrorDTO()) // Manejo de cuerpo vacío para evitar NullPointerException
                                .flatMap(errorDto -> {
                                    String msg = "Error al llamar al API /dishes: "
                                            + (errorDto.getMessage() != null ? errorDto.getMessage() : "sin detalle");
                                    // Lanzamos la excepción que tu aplicación ya tiene definida
                                    return Mono.error(new ConnectApiRestException(msg));
                                })
                )
                .bodyToMono(new ParameterizedTypeReference<PageResponse<DishDTO>>() {})
                .block();
    }
}