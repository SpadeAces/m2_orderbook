package com.interview.m2_orderbook.dto;

import java.util.List;
import java.util.Map;

public class OrderBook {
    Map<String, List<Order>> bids;
    Map<String, List<Order>> asks;
    Map<String, List<Order>> tradedOrder;

    public OrderBook(Map<String, List<Order>> bids, Map<String, List<Order>> asks, Map<String, List<Order>> tradedOrder) {
        this.bids = bids;
        this.asks = asks;
        this.tradedOrder =tradedOrder;
    }

    public Map<String, List<Order>> getBids() {
        return bids;
    }

    public void setBids(Map<String, List<Order>> bids) {
        this.bids = bids;
    }

    public Map<String, List<Order>> getAsks() {
        return asks;
    }

    public void setAsks(Map<String, List<Order>> asks) {
        this.asks = asks;
    }

    public Map<String, List<Order>> getTradedOrder() {
        return tradedOrder;
    }

    public void setTradedOrder(Map<String, List<Order>> tradedOrder) {
        this.tradedOrder = tradedOrder;
    }
}
