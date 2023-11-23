package com.interview.m2_orderbook.dto;

import com.interview.m2_orderbook.constants.OrderType;
import com.interview.m2_orderbook.constants.Side;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "order")
public class Order {
    @Transient
    public static final String SEQUENCE_NAME = "ORDER_SEQUENCE";

    @Id
    private long id;
    private String symbol;
    private Double orderQuantity;
    private Double totalQuantity;
    private Double price;
    private Side side;
    private OrderType orderType;
    private Boolean iceBerg;
    private Double icebergQuantity;
    private Double remainingQuantity;
    private Boolean isBracketedOrder;

    private TakeProfitStopLoss takeProfitStopLoss;
    public Order(long id,String symbol, Double totalQuantity, Double price,
                 Side side, OrderType orderType, Boolean iceBerg, Double icebergQuantity,
                 Boolean isBracketedOrder, TakeProfitStopLoss takeProfitStopLoss) {
        this.id = id;
        this.symbol = symbol;
        this.totalQuantity = totalQuantity;
        this.price = price;
        this.side = side;
        this.orderType = orderType;
        this.iceBerg = iceBerg != null && iceBerg;
        this.icebergQuantity = icebergQuantity;
        this.remainingQuantity = Boolean.TRUE.equals(iceBerg) ? totalQuantity - totalQuantity / icebergQuantity : 0.0;
        this.orderQuantity =  Boolean.TRUE.equals(iceBerg) ? totalQuantity - remainingQuantity : totalQuantity;
        this.isBracketedOrder = isBracketedOrder!=null && isBracketedOrder;
        this.takeProfitStopLoss = takeProfitStopLoss;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public Double getTotalQuantity() {
        return totalQuantity;
    }

    public void setTotalQuantity(Double totalQuantity) {
        this.totalQuantity = totalQuantity;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Side getSide() {
        return side;
    }

    public void setSide(Side side) {
        this.side = side;
    }

    public OrderType getOrderType() {
        return orderType;
    }

    public void setOrderType(OrderType orderType) {
        this.orderType = orderType;
    }

    public Boolean isIceBerg() {
        return iceBerg;
    }

    public void setIceBerg(Boolean iceBerg) {
        this.iceBerg = iceBerg;
    }

    public Double getIcebergQuantity() {
        return icebergQuantity;
    }

    public void setIcebergQuantity(Double icebergQuantity) {
        this.icebergQuantity = icebergQuantity;
    }

    public Double getOrderQuantity() {
        return orderQuantity;
    }

    public void setOrderQuantity(Double orderQuantity) {
        this.orderQuantity = orderQuantity;
    }

    public Double getRemainingQuantity() {
        return remainingQuantity;
    }

    public void setRemainingQuantity(Double remainingQuantity) {
        this.remainingQuantity = remainingQuantity;
    }

    public Boolean isBracketedOrder() {
        return isBracketedOrder;
    }

    public void setBracketedOrder(Boolean bracketedOrder) {
        isBracketedOrder = bracketedOrder;
    }

    public TakeProfitStopLoss getTakeProfitStopLoss() {
        return takeProfitStopLoss;
    }

    public void setTakeProfitStopLoss(TakeProfitStopLoss takeProfitStopLoss) {
        this.takeProfitStopLoss = takeProfitStopLoss;
    }
}