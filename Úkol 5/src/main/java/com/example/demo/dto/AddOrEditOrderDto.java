package com.example.demo.dto;

import com.example.demo.entity.OrderHasProduct;
import com.example.demo.entity.Person;
import com.example.demo.entity.State;

import java.util.Set;

public class AddOrEditOrderDto {

    private Long id;

    private Person person;

    private State state;

    private Set<OrderHasProduct> orderHasProducts;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Person getPerson() {
        return person;
    }

    public void setPerson(Person person) {
        this.person = person;
    }

    public State getState() {
        return state;
    }

    public void setState(State state) {
        this.state = state;
    }

    public Set<OrderHasProduct> getOrderHasProducts() {
        return orderHasProducts;
    }

    public void setOrderHasProducts(Set<OrderHasProduct> orderHasProducts) {
        this.orderHasProducts = orderHasProducts;
    }
}
