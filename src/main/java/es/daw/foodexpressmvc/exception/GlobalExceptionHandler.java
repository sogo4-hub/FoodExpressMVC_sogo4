package es.daw.foodexpressmvc.exception;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(ConnectApiException.class)
    public String handleConnectApiException(ConnectApiException e, Model model){
        model.addAttribute("errorMessage", e.getMessage());
        return "api-error";
    }
}
