package org.example.foodordersystem.web;

import lombok.RequiredArgsConstructor;
import org.example.foodordersystem.domain.Item;
import org.example.foodordersystem.domain.Order;
import org.example.foodordersystem.service.MenuService;
import org.example.foodordersystem.service.OrderService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.util.UUID;

@Controller
@RequiredArgsConstructor
@RequestMapping("/admin")
public class AdminController {

    private final MenuService menuService;
    private final OrderService orderService;

    @PostMapping("/menu/addItem")
    public String addItemToMenu(Item item) {
        menuService.addItem(item);
        return "redirect:/admin/menu";
    }

    @PostMapping("/menu/deleteItem")
    public String deleteItemFromMenu(@RequestParam("itemId") UUID itemId) {
        menuService.deleteItem(itemId);
        return "redirect:/admin/menu";
    }

    @GetMapping("/orders")
    public ModelAndView showOrders() {
        return new ModelAndView("active-orders")
                .addObject("orders", orderService.getAllOrders());
    }

    @PostMapping("/orders/confirm")
    public String confirmOrder(@RequestParam("orderId") UUID orderId) {
        orderService.completeOrder(orderId);
        return "redirect:/admin/orders";
    }

    @GetMapping("/menu")
    public ModelAndView showMenu() {
        return new ModelAndView("admin-menu")
                .addObject("menu", menuService.getMenu())
                .addObject("newItem", new Item());
    }
}
