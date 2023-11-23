package com.interview.m2_orderbook.repository;

import com.interview.m2_orderbook.dto.Order;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;
import java.util.Optional;

public interface OrderRepository extends MongoRepository<Order, String> {
    List<Order> findBySymbol(String symbol);
    Optional<Order> findById(long id);
}
