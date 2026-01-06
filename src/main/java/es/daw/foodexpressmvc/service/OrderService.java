package es.daw.foodexpressmvc.service;

import es.daw.foodexpressmvc.dto.OrderDTO;
import es.daw.foodexpressmvc.exception.ConnectApiRestException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderService {
    private final WebClient webClientAPI;

    public List<OrderDTO> getFilteredOrders(String status, Long userId, Long restaurantId,String sortBy, String direction) {
        return webClientAPI.get()
                .uri(uriBuilder -> uriBuilder
                        .path("/orders")
                        .queryParam("status", status)
                        .queryParam("userId", userId)
                        .queryParam("restaurantId", restaurantId)
                        .queryParam("sortBy", sortBy)       // Nuevo
                        .queryParam("direction", direction) // Nuevo
                        .build())
                .retrieve()
                .onStatus(HttpStatusCode::isError, response ->
                        response.bodyToMono(String.class).flatMap(msg ->
                                Mono.error(new ConnectApiRestException(msg)))) // Captura el mensaje de la excepción de la API
                .bodyToFlux(OrderDTO.class)
                .collectList()
                .block();
    }
}
