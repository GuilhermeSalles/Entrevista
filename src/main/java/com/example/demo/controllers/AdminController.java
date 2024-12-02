package com.example.demo.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AdminController {

    @GetMapping("/admin/dashboard")
    public String adminDashboard() {
        return "admin-dashboard";
    }

    @GetMapping("/admin/accounts")
    public String listAccounts(Model model) {
        // Adicione lógica para buscar contas
        model.addAttribute("accounts", null); // Substituir por lista de contas reais
        return "list-accounts";
    }

    @GetMapping("/admin/accounts/create")
    public String createAccountForm() {
        return "create-account";
    }
}
