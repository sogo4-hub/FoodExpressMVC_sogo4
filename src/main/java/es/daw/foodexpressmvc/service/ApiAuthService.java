package es.daw.foodexpressmvc.service;

import es.daw.foodexpressmvc.dto.ApiLoginRequest;
import es.daw.foodexpressmvc.dto.ApiLoginResponse;
import es.daw.foodexpressmvc.exception.ConnectApiException;
import es.daw.foodexpressmvc.exception.ConnectApiRestException;
import es.daw.foodexpressmvc.session.ApiSessionToken;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

/*
Spring Boot MVC es un entorno servlet, y Spring inyecta automáticamente:
HttpSession
HttpServletRequest
HttpServletResponse
Principal
*/import java.util.Map;

@Service
@RequiredArgsConstructor
public class ApiAuthService {
    private final WebClient webClientAuth;
    @Value("${api.auth-username}")
    private String apiUsername;
    @Value("${api.auth-password}")
    private String apiPassword;
//    private final HttpSession httpSession;
    private final ApiSessionToken apiSessionToken;
    public String getToken(){
        //FORMA 1: USANDO HTTPSESSION
//        String storedToken = (String) httpSession.getAttribute("token");
//        if(storedToken!=null) {
//            return storedToken;
//        }

        //FORMA 2: Usando Componet de sesssionScope
        if(apiSessionToken.getApiToken()!=null){
            return apiSessionToken.getApiToken();
        }

        //Si es nulo hago el login.........
        System.out.println("*********************pidiendo token********************");
        ApiLoginRequest request = new ApiLoginRequest();
        request.setUsername(apiUsername);
        request.setPassword(apiPassword);
        try {
            ApiLoginResponse response = webClientAuth
                    .post()
                    .uri("/login")
                    .bodyValue(request)
                    .retrieve()
                    .bodyToMono(ApiLoginResponse.class)
                    .block();//sincrona
            if (response == null || response.getToken() == null) {
                throw new ConnectApiRestException("API login failed: no token received");
            }
//            httpSession.setAttribute("token", response.getToken());
            apiSessionToken.setApiToken(response.getToken());
            return response.getToken();
        } catch (Exception e) {
            throw new ConnectApiRestException(e.getMessage());
        }
    }
}
