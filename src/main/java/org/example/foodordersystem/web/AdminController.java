package org.example.foodordersystem.web;

import lombok.RequiredArgsConstructor;
import org.example.foodordersystem.domain.Item;
import org.example.foodordersystem.domain.Order;
import org.example.foodordersystem.service.MenuService;
import org.example.foodordersystem.service.OrderService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequiredArgsConstructor
public class AdminController {

    private final MenuService menuService;
    private final OrderService orderService;

    @PostMapping("/menu/addItem")
    public String addItemToMenu(Item item) {
        menuService.addItem(item);
        return "redirect:/menu";
    }

    @PostMapping("/menu/deleteItem")
    public String deleteItemFromMenu(@RequestBody Item item) {
        menuService.deleteItem(item);
        return "redirect:/menu";
    }

    @GetMapping("/orders")
    public ModelAndView showOrders() {
        return new ModelAndView("active-orders")
                .addObject("orders", orderService.getAllOrders());
    }

    @PostMapping("/orders/confirm")
    public String confirmOrder(@RequestBody Order order) {
        orderService.completeOrder(order);
        return "redirect:/orders";
    }
}
