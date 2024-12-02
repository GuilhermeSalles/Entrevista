package com.example.demo.controllers;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.demo.dtos.AccountDTO;
import com.example.demo.services.AccountService;

@Controller
@RequestMapping("/thymeleaf/accounts")
public class AccountThymeleafController {

    @Autowired
    private AccountService accountService;

    @GetMapping
    public String listAccounts(Model model) {
        List<AccountDTO> accounts = accountService.listAllAccounts();
        model.addAttribute("accounts", accounts);
        return "list-accounts";
    }

    @GetMapping("/create")
    public String showCreateAccountForm() {
        return "create-account";
    }

    @PostMapping("/create")
    public String createAccount(@RequestParam String accountNumber,
                                @RequestParam String ownerName,
                                @RequestParam Double balance) {
        AccountDTO accountDTO = new AccountDTO(accountNumber, ownerName, balance);
        accountService.createAccount(accountDTO);
        return "redirect:/thymeleaf/accounts";
    }
}
