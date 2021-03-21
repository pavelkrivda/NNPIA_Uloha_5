package com.example.demo.controller;

import com.example.demo.dto.AddOrEditOrderDto;
import com.example.demo.entity.Order;
import com.example.demo.repository.OrderRepository;
import com.example.demo.service.ShoppingCartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class OrderController {

    private final ShoppingCartService shoppingCartService;
    @Autowired
    private OrderRepository orderRepository;

    public OrderController(ShoppingCartService shoppingCartService) {
        this.shoppingCartService = shoppingCartService;
    }

    @GetMapping("/checkout")
    public String checkout() {
        shoppingCartService.checkout();
        return "order/checkout";
    }

    @GetMapping("/storno")
    public String storno() {
        shoppingCartService.storno();
        return "redirect:/";
    }

    @GetMapping("/order-detail/{id}")
    public String showAllOrder(@PathVariable(required = false) Long id, Model model) {
        System.out.println();
        if (id != null) {
            model.addAttribute("order", orderRepository.findById(id).get());
            return "order/order-detail";
        }

        return "redirect:/";
    }

    @GetMapping(value = {"/order-form", "/order-form/{id}"})
    public String showOrderForm(@PathVariable(required = false) Long id, Model model) {

        if (id != null) {
            Order order = orderRepository.findById(id).orElse(new Order());

            AddOrEditOrderDto dto = new AddOrEditOrderDto();
            dto.setId(order.getId());
            dto.setState(order.getState());

            model.addAttribute("order", dto);
        } else {
            model.addAttribute("order", new AddOrEditOrderDto());
        }

        return "order/order-form";
    }

    @PostMapping("/order-form-process")
    public String addOrderProcess(AddOrEditOrderDto addOrEditOrderDto) {
        Order order = new Order();
        order.setId(addOrEditOrderDto.getId());
        order.setState(addOrEditOrderDto.getState());

        orderRepository.save(order);
        return "redirect:/order";
    }

    @GetMapping("/order-remove/{id}")
    public String orderRemove(@PathVariable(required = false) Long id) {

        if (id != null) {
            Order order = orderRepository.findById(id).get();
            orderRepository.delete(order);
        }

        return "redirect:/order";
    }
}
