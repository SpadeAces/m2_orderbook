package com.interview.m2_orderbook.services;

import com.interview.m2_orderbook.constants.Side;
import com.interview.m2_orderbook.dto.Order;
import com.interview.m2_orderbook.dto.OrderBook;
import com.interview.m2_orderbook.dto.TakeProfitStopLoss;
import com.interview.m2_orderbook.dto.common.ApiResponse;
import com.interview.m2_orderbook.repository.OrderRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class OrderService {
    private OrderRepository orderRepository;

    public OrderService(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    // method to add order to db
    public ApiResponse<Order> addOrder(Order order) {
        if (order.getTotalQuantity() < 0 || order.getPrice() <= 0) {
            return new ApiResponse<>(HttpStatus.BAD_REQUEST.value(), "Invalid order", null);
        }
        if (order.isIceBerg()){
            if (!isValidIceBergOrder(order))
                new ApiResponse<>(HttpStatus.BAD_REQUEST.value(),
                        "Invalid iceberg order", null);
        }
        if(order.isBracketedOrder())
            order = calculateProfitAndLoss(order);

        return new ApiResponse<>(HttpStatus.CREATED.value(),
                "Order created successfully", orderRepository.save(order));
    }

    //method to get order book
    public ApiResponse<OrderBook> getOrderBook(String symbol) {
        Map<String, List<Order>> bids = new TreeMap<>(Collections.reverseOrder());
        Map<String, List<Order>> asks = new TreeMap<>();

        Map<String, List<Order>> tradedOrder = new TreeMap<>();
        orderRepository.findBySymbol(symbol)
                .forEach(order -> {
                    if(order.getTotalQuantity().equals(0.0))
                        tradedOrder.computeIfAbsent(symbol, k->new ArrayList<>()).add(order);
                    else if (order.getSide().equals(Side.BID))
                        bids.computeIfAbsent(symbol, k -> new ArrayList<>()).add(order);
                    else
                        asks.computeIfAbsent(symbol, k -> new ArrayList<>()).add(order);
                });
        bids.values().forEach(bidList -> bidList.sort(Comparator.comparing(Order::getPrice).reversed()));

        asks.values().forEach(bidList -> bidList.sort(Comparator.comparing(Order::getPrice)));
        return new ApiResponse<>(HttpStatus.OK.value(), "order book fetched", new OrderBook(bids,asks,tradedOrder));
    }

    //method to update order
    public ApiResponse updateOrder(Order order) {
        Order repoOrder = orderRepository.findById(order.getId()).orElse(null);
        if (repoOrder != null) {
            if (order.isBracketedOrder())
                repoOrder = calculateProfitAndLoss(order);
            return new ApiResponse(HttpStatus.OK.value(), "Order updated successfully", orderRepository.save(repoOrder));
        }
        return new ApiResponse(HttpStatus.OK.value(), "No order found",order);
    }

    //method to get details of specific order
    public ApiResponse getOrder(long id) {
        Order repoOrder = orderRepository.findById(id).orElse(null);
        return repoOrder == null ? new ApiResponse(HttpStatus.OK.value(),
                "No order found against id " + id,null):
                new ApiResponse(HttpStatus.OK.value(), "Order fetched successfully", repoOrder);
    }

    //method to validate if the iceberg order is valid order to write in order book
    private Boolean isValidIceBergOrder(Order order ){
        return order.getIcebergQuantity() == null &&
                order.getIcebergQuantity()  == 0 &&
                order.getIcebergQuantity() < 10 &&
                order.getIcebergQuantity() < order.getTotalQuantity();
    }

    //calculate profit and loss for bracketed order
    private Order calculateProfitAndLoss(Order order) {
        // calculate profit and loss base on bid and ask before writing to db
        TakeProfitStopLoss takeProfitStopLoss = order.getTakeProfitStopLoss();
        if (order.getSide() == Side.BID) {
            takeProfitStopLoss.setProfit(
                    (takeProfitStopLoss.getUpperLimit() - order.getPrice()) * order.getTotalQuantity());
            takeProfitStopLoss.setLoss(
                    (order.getPrice() - takeProfitStopLoss.getLowerLimit()) * order.getTotalQuantity());
        } else if (order.getSide() == Side.ASK){
            takeProfitStopLoss.setLoss(
                    (takeProfitStopLoss.getUpperLimit() - order.getPrice()) * order.getTotalQuantity());
            takeProfitStopLoss.setProfit(
                    (order.getPrice() - takeProfitStopLoss.getLowerLimit()) * order.getTotalQuantity());
        }
        order.setTakeProfitStopLoss(takeProfitStopLoss);
        return order;
    }
}