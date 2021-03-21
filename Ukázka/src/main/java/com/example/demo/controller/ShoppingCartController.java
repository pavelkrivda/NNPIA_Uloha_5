package com.example.demo.controller;

import com.example.demo.service.ShoppingCartService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class ShoppingCartController {

    private final ShoppingCartService shoppingCartService;

    public ShoppingCartController(ShoppingCartService shoppingCartService) {
        this.shoppingCartService = shoppingCartService;
    }

    @GetMapping("/shopping-cart-add/{id}")
    public String shoppingCartAdd(@PathVariable(required = false) Long id, Model model) {
        shoppingCartService.add(id);
        return "redirect:/shopping-cart";
    }

    @GetMapping("/shopping-cart-remove/{id}")
    public String shoppingCartRemove(@PathVariable(required = false) Long id, Model model) {
        shoppingCartService.remove(id);
        return "redirect:/shopping-cart";
    }

    @GetMapping("/shopping-cart")
    public String showShoppingCartAdd(Model model) {
        model.addAttribute("shoppingCart", shoppingCartService.getCart());
        return "shopping-cart";
    }
}
