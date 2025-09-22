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
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequiredArgsConstructor
public class UserController {

    private final OrderService orderService;
    private final MenuService menuService;

    @GetMapping("/menu")
    public ModelAndView showMenu() {
        return new ModelAndView().addObject("menu", menuService.getMenu());
    }

    @PostMapping("/order")
    public ModelAndView createOrder(@RequestBody Customer customer) {
        return new ModelAndView("order")
                .addObject("order", orderService.createOrder(customer));
    }

    @PostMapping("/order/addItem")
    public ModelAndView addItemToOrder(@RequestBody Order order, @RequestBody Item item, ModelAndView model) {
        model.addObject("order", orderService.addItemToOrder(order, item));
        return model;
    }

    @PostMapping("/order/send")
    public String sendOrder(@RequestBody Order order) {
        orderService.sendOrder(order);
        return "redirect:/menu";
    }

}
