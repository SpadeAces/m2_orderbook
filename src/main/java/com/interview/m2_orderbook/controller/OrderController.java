package com.interview.m2_orderbook.controller;

import com.interview.m2_orderbook.dto.common.ApiResponse;
import com.interview.m2_orderbook.dto.Order;
import com.interview.m2_orderbook.services.OrderService;
import com.interview.m2_orderbook.services.SequenceGeneratorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("order")
public class OrderController {
    @Autowired private OrderService orderService;
    @Autowired private SequenceGeneratorService sequenceGeneratorService;

    @PostMapping()
    //end point to add order to order book
    public ApiResponse addOrder(@RequestBody Order order){
        order.setId(sequenceGeneratorService.generateSequence(Order.SEQUENCE_NAME));
        System.out.println(order);
        return orderService.addOrder(order);
    }

    @GetMapping("get_orderbook")
    //end point to get order book
    public ApiResponse getOrderBook(@RequestParam String symbol){
        return orderService.getOrderBook(symbol);
    }

    @PostMapping("update_order")
    //end point to update order book, make a bracket order with existing non bracketed order or update SLTP limit
    public ApiResponse updateOrder(@RequestBody Order order){
        return orderService.updateOrder(order);
    }

    @GetMapping
    //end point to get the details of specific order
    public ApiResponse getOrder(@RequestParam long id){
        return orderService.getOrder(id);
    }
}
