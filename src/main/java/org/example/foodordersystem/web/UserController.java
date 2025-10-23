package org.example.foodordersystem.web;

import lombok.RequiredArgsConstructor;
import org.example.foodordersystem.domain.Customer;
import org.example.foodordersystem.domain.Item;
import org.example.foodordersystem.domain.Order;
import org.example.foodordersystem.service.MenuService;
import org.example.foodordersystem.service.OrderService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.util.UUID;

@Controller
@RequiredArgsConstructor
public class UserController {

    private final OrderService orderService;
    private final MenuService menuService;

    @GetMapping("/menu")
    public ModelAndView showMenu() {
        return new ModelAndView("menu")
                .addObject("menu", menuService.getMenu())
                .addObject("customer", new Customer());
    }

    @PostMapping("/order")
    public ModelAndView createOrder(Customer customer) {
        Order order = orderService.createOrder(customer);
        return new ModelAndView("order")
                .addObject("order", order)
                .addObject("menu", menuService.getMenu());
    }

    @PostMapping("/order/addItem")
    public ModelAndView addItemToOrder(@RequestParam("orderId") UUID orderId,
                                       @RequestParam("itemId") UUID itemId) {
        Order updatedOrder = orderService.addItemToOrder(orderId, itemId);
        return new ModelAndView("order")
                .addObject("order", updatedOrder)
                .addObject("menu", menuService.getMenu());
    }

    @PostMapping("/order/send")
    public String sendOrder(@RequestParam("orderId") UUID orderId) {
        return "redirect:/menu";
    }
}
