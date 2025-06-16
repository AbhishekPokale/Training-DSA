package com.assignment.orderstatemachine.controller;

import com.assignment.orderstatemachine.model.Order;
import com.assignment.orderstatemachine.model.OrderEvent;
import com.assignment.orderstatemachine.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/orders")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @PostMapping
    public Order createOrder(@RequestParam String description) {
        return orderService.createOrder(description);
    }

    @PostMapping("/{orderId}/event/{event}")
    public String sendEvent(@PathVariable Long orderId, @PathVariable String event) {
        try {
            OrderEvent orderEvent = OrderEvent.valueOf(event.toUpperCase());
            orderService.processEvent(orderId, orderEvent);
            return "Order " + orderId + " updated successfully.";
        } catch (IllegalArgumentException e) {
            return " Invalid event: " + event;
        } catch (Exception e) {
            return "Error: " + e.getMessage();
        }
    }
}
