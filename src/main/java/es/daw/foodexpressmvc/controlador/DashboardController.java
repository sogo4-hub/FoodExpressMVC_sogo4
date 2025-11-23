package es.daw.foodexpressmvc.controlador;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.security.Principal;

@Controller
public class DashboardController {

    @GetMapping("/dashboard")
    public String showDashboard(Model model, Principal principal){
        //Aqui principal es el usuario que se ha logueado
        //registry.addViewController("/home").setViewName("dashboard");
        if (principal != null){
            model.addAttribute("username", principal.getName());
        }
        return "dashboard";
    }
}
