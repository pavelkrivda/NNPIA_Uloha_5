package com.example.demo.repository;

import com.example.demo.entity.Order;
import com.example.demo.entity.Person;
import com.example.demo.entity.State;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.Period;
import java.util.Optional;
import java.util.Stack;

public interface OrderRepository extends JpaRepository<Order, Long> {

    @EntityGraph(attributePaths = "orderHasProducts")
    Optional<Order> findById(Long id);
}
