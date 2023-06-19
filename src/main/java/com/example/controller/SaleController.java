package com.example.controller;

import com.example.entity.Sale;
import com.example.exception.EntityAlreadyExistException;
import com.example.exception.EntityNotFoundException;
import com.example.repositories.SaleRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/sale")
public class SaleController {
    @Autowired
    private final SessionRegistry sessionRegistry;
    @Autowired
    private final SaleRepo saleRepo;
    public SaleController(SessionRegistry sessionRegistry, SaleRepo saleRepo) {
        this.sessionRegistry = sessionRegistry;
        this.saleRepo = saleRepo;
    }

    @GetMapping("/usersLogged")
    public String getUsersOnline(Model model) {
        List a = getLoggedInUsers();
        model.addAttribute("users", a);
        System.out.println(a);
        return "usersOnline";
    }

    @GetMapping("/{id}")
    public String getSaleById(@PathVariable("id") long id, Model model) {
        try {
            model.addAttribute("sale", saleRepo.getSaleById(id));
            return "sale/getSaleById";
        } catch (EntityNotFoundException exception) {
            return exception.getMessage();
        } catch (Exception e) {
            return e.getMessage();
        }
    }

    @PostMapping()
    public String getSale(@RequestParam long id) {
        return "redirect:/sale/" + id;
    }

    @GetMapping("/getAllSales")
    public String getAllSales(Model model) {
        try {
            model.addAttribute("allSales", saleRepo.getAllSale());
            return "sale/getAllSales";
        } catch (Exception e) {
            return e.getMessage();
        }

    }

    @PostMapping("/createSale")
    public String createSale(@RequestParam long id,
                             @RequestParam int sumSale,
                             @RequestParam String dateReceiptGoods,
                             @RequestParam String dateSale,
                             @RequestParam long idGoods) {
        try {
            Sale sale = new Sale(id, sumSale, dateReceiptGoods, dateSale, idGoods);
            saleRepo.createSale(sale);
            return "sale/createdSale";
        } catch (EntityAlreadyExistException exception) {
            return exception.getMessage();
        } catch (Exception e) {
            return e.getMessage();
        }

    }

    private List<String> getLoggedInUsers() {
        final List<Object> allPrincipals = sessionRegistry.getAllPrincipals();
        List<UserDetails> userList = new ArrayList<>();
        List<String> usernames = new ArrayList<>();
        for (final Object principal : allPrincipals) {
            if (principal instanceof UserDetails) {
                UserDetails user = (UserDetails) principal;
                userList.add(user);
                usernames.add(user.getUsername());
            }
        }
        return usernames;
    }
}
