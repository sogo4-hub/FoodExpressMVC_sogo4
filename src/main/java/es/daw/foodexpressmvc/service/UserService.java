package es.daw.foodexpressmvc.service;

import es.daw.foodexpressmvc.dto.UserDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import java.util.Arrays;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {
    private final WebClient webClientAPI;

    public List<UserDTO> getAllUsersFromApi() {
        List<UserDTO> allUsers= webClientAPI.get()
                .uri("/users") // Llama al endpoint de la API
                .retrieve()
                .bodyToFlux(UserDTO.class)
                .collectList()
                .block();
        System.out.println(allUsers);
        return allUsers;
    }
}

