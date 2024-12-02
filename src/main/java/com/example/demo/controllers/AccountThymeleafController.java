package com.example.demo.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.demo.dtos.AccountDTO;
import com.example.demo.services.AccountService;

@Controller
@RequestMapping("/accounts")
public class AccountThymeleafController {

    @Autowired
    private AccountService accountService;

    @GetMapping("/list")
    public String listAccounts(Model model) {
        List<AccountDTO> accounts = accountService.listAllAccounts();
        model.addAttribute("accounts", accounts);
        return "index"; // Nome do arquivo em templates (index.html)
    }
}
