package com.example.demo.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class LoginController {

    @GetMapping("/login")
    public String login() {
        return "login"; // Página de login
    }

    @PostMapping("/login")
    public String authenticate(@RequestParam String username, @RequestParam String password, Model model) {
        // Simulação de autenticação (substitua com lógica real de autenticação)
        if ("admin".equals(username) && "admin123".equals(password)) {
            return "redirect:/admin/dashboard"; // Redireciona para o dashboard do admin
        } else if ("user".equals(username) && "user123".equals(password)) {
            model.addAttribute("username", username);
            return "redirect:/user/dashboard"; // Redireciona para o dashboard do usuário
        }
        model.addAttribute("error", "Usuário ou senha inválidos");
        return "login";
    }
}
																															