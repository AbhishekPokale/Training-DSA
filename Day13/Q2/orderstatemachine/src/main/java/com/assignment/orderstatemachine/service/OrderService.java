package com.assignment.orderstatemachine.service;

import com.assignment.orderstatemachine.model.InvalidOrderStateException;
import com.assignment.orderstatemachine.model.Order;
import com.assignment.orderstatemachine.model.OrderEvent;
import com.assignment.orderstatemachine.model.OrderState;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.statemachine.StateMachine;
import org.springframework.statemachine.config.StateMachineFactory;
import org.springframework.stereotype.Service;
import org.springframework.statemachine.support.DefaultStateMachineContext;


import java.util.HashMap;
import java.util.Map;
import java.util.Random;

@Service
public class OrderService {

    private final Map<Long, Order> orders = new HashMap<>();
    private final StateMachineFactory<OrderState, OrderEvent> factory;
    private final Random random = new Random();

    @Autowired
    public OrderService(StateMachineFactory<OrderState, OrderEvent> factory) {
        this.factory = factory;
    }

    public Order createOrder(String description) {
        long id = random.nextLong(10000);
        Order order = new Order(id, description);
        orders.put(id, order);
        System.out.println("✅ Created new order: " + order.getId() + " in state: " + order.getState());
        return order;
    }

    public void processEvent(Long orderId, OrderEvent event) {
        Order order = orders.get(orderId);
        if (order == null) {
            throw new IllegalArgumentException("Order not found: " + orderId);
        }

        StateMachine<OrderState, OrderEvent> stateMachine = factory.getStateMachine(orderId.toString());
        stateMachine.stop();
        stateMachine.getStateMachineAccessor().doWithAllRegions(access -> {
            access.resetStateMachine(
                    new DefaultStateMachineContext<OrderState, OrderEvent>(
                            order.getState(), null, null, null, null, orderId.toString()
                    )
            );
        });
        stateMachine.start();

        Message<OrderEvent> message = MessageBuilder.withPayload(event)
                .setHeader("orderId", orderId)
                .build();

        boolean accepted = stateMachine.sendEvent(message);
        if (!accepted) {
            throw new InvalidOrderStateException("Invalid event '" + event + "' for current state '" + order.getState() + "'");
        }

        OrderState newState = stateMachine.getState().getId();
        order.setState(newState);
        System.out.println("➡️ Order " + orderId + " transitioned to " + newState);
    }
}
