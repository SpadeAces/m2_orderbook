package com.interview.m2_orderbook.dto.common;

import com.interview.m2_orderbook.constants.OrderType;
import com.interview.m2_orderbook.constants.Side;
import com.interview.m2_orderbook.dto.Order;
import com.interview.m2_orderbook.dto.TakeProfitStopLoss;
import com.interview.m2_orderbook.services.OrderService;
import com.interview.m2_orderbook.services.SequenceGeneratorService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class DataSeeder implements CommandLineRunner {
    private OrderService orderService;

    private SequenceGeneratorService sequenceGeneratorService;

    public DataSeeder(OrderService orderService, SequenceGeneratorService sequenceGeneratorService) {
        this.orderService = orderService;
        this.sequenceGeneratorService = sequenceGeneratorService;
    }

    @Override
    public void run(String... args) throws Exception {
        Order marketBid  =  new Order(sequenceGeneratorService.generateSequence(Order.SEQUENCE_NAME),"SOLUSDT", 10.0,55.0,
                Side.BID, OrderType.MARKET,false,0.0,false, null);

        orderService.addOrder(marketBid);

        Order marketAsk  =  new Order(sequenceGeneratorService.generateSequence(Order.SEQUENCE_NAME),"SOLUSDT", 10.0,55.0,
                Side.ASK, OrderType.MARKET,false,0.0,false, null);

        orderService.addOrder(marketAsk);

        Order limitBid  =  new Order(sequenceGeneratorService.generateSequence(Order.SEQUENCE_NAME),"SOLUSDT", 10.0,54.5,
                Side.BID, OrderType.LIMIT,false,0.0,false, null);

        orderService.addOrder(limitBid);

        Order limitAsk  =  new Order(sequenceGeneratorService.generateSequence(Order.SEQUENCE_NAME),"SOLUSDT", 10.0,55.5,
                Side.ASK, OrderType.LIMIT,false,0.0,false, null);

        orderService.addOrder(limitAsk);

        TakeProfitStopLoss takeProfitStopLoss = new TakeProfitStopLoss(55.5, 54.5);

        Order limitBidBracketOrder  =  new Order(sequenceGeneratorService.generateSequence(Order.SEQUENCE_NAME),"SOLUSDT", 10.0,55.0,
                Side.BID, OrderType.LIMIT,false,0.0,true, takeProfitStopLoss);

        orderService.addOrder(limitBidBracketOrder);

        Order limitASKBracketOrder  =  new Order(sequenceGeneratorService.generateSequence(Order.SEQUENCE_NAME),"SOLUSDT", 10.0,55.0,
                Side.ASK, OrderType.LIMIT,false,0.0,true, takeProfitStopLoss);

        orderService.addOrder(limitASKBracketOrder);

        Order MarketASKBracketIcebergOrder  =  new Order(sequenceGeneratorService.generateSequence(Order.SEQUENCE_NAME),"SOLUSDT", 10.0,55.0,
                Side.ASK, OrderType.LIMIT,true,5.0,true, takeProfitStopLoss);

        orderService.addOrder(MarketASKBracketIcebergOrder);

        Order MarketBidBracketIcebergOrder  =  new Order(sequenceGeneratorService.generateSequence(Order.SEQUENCE_NAME),"SOLUSDT", 10.0,55.0,
                Side.BID, OrderType.LIMIT,true,5.0,true, takeProfitStopLoss);

        orderService.addOrder(MarketBidBracketIcebergOrder);

        Order tradedOrder1  =  new Order(sequenceGeneratorService.generateSequence(Order.SEQUENCE_NAME),"SOLUSDT", 0.0,55.0,
                Side.BID, OrderType.LIMIT,true,5.0,true, takeProfitStopLoss);

        orderService.addOrder(tradedOrder1);

        Order tradedOrder2  =  new Order(sequenceGeneratorService.generateSequence(Order.SEQUENCE_NAME),"SOLUSDT", 0.0,55.0,
                Side.ASK, OrderType.MARKET,true,5.0,true, takeProfitStopLoss);

        orderService.addOrder(tradedOrder2);

        System.out.println("-----------------data seeded to db-------------------------------");
    }
}