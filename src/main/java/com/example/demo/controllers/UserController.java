package com.example.demo.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import com.example.demo.entities.Account;
import com.example.demo.services.AccountService;

@Controller
@RequestMapping("/user")
public class UserController {

    @Autowired
    private AccountService accountService;

    @GetMapping("/dashboard")
    public String userDashboard() {
        return "user-dashboard"; // Página inicial para entrada do número da conta
    }

    @PostMapping("/dashboard")
    public String loadUserAccount(@RequestParam String accountNumber, Model model) {
        try {
            Account account = accountService.findByAccountNumber(accountNumber);
            model.addAttribute("account", account);
            return "user-dashboard-loaded"; // Carrega o dashboard com as informações da conta
        } catch (RuntimeException e) {
            model.addAttribute("error", "Conta não encontrada!");
            return "user-dashboard"; // Retorna para a página de entrada do número da conta com mensagem de erro
        }
    }

    @GetMapping("/deposit")
    public String depositPage(@RequestParam String accountNumber, Model model) {
        model.addAttribute("accountNumber", accountNumber);
        return "deposit";
    }

    @PostMapping("/deposit")
    public String makeDeposit(@RequestParam String accountNumber, @RequestParam Double amount, Model model) {
        try {
            Account account = accountService.findByAccountNumber(accountNumber);
            accountService.creditAccount(account.getId(), amount);
            model.addAttribute("message", "Depósito realizado com sucesso!");
            return "redirect:/user/dashboard";
        } catch (IllegalArgumentException e) {
            model.addAttribute("error", e.getMessage());
            return "deposit";
        }
    }

    @GetMapping("/transfer")
    public String transferPage(@RequestParam String accountNumber, Model model) {
        model.addAttribute("accountNumber", accountNumber);
        return "transfer";
    }

    @PostMapping("/transfer")
    public String makeTransfer(@RequestParam String originAccount, @RequestParam String destinationAccount,
                               @RequestParam Double amount, Model model) {
        try {
            Account origin = accountService.findByAccountNumber(originAccount);
            Account destination = accountService.findByAccountNumber(destinationAccount);
            accountService.transfer(origin.getId(), destination.getId(), amount);
            model.addAttribute("message", "Transferência realizada com sucesso!");
            return "redirect:/user/dashboard";
        } catch (IllegalArgumentException e) {
            model.addAttribute("error", e.getMessage());
            return "transfer";
        }
    }

    @GetMapping("/withdraw")
    public String withdrawPage(@RequestParam String accountNumber, Model model) {
        model.addAttribute("accountNumber", accountNumber);
        return "withdraw";
    }

    @PostMapping("/withdraw")
    public String makeWithdrawal(@RequestParam String accountNumber, @RequestParam Double amount, Model model) {
        try {
            Account account = accountService.findByAccountNumber(accountNumber);
            accountService.debitAccount(account.getId(), amount);
            model.addAttribute("message", "Saque realizado com sucesso!");
            return "redirect:/user/dashboard";
        } catch (IllegalArgumentException e) {
            model.addAttribute("error", e.getMessage());
            return "withdraw";
        }
    }
}
