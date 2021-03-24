package com.example.demo.datafactory;

import com.example.demo.entity.Order;
import com.example.demo.entity.Person;
import com.example.demo.entity.State;
import com.example.demo.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class OrderTestDataFactory {

    public static final State TEST_STATE = State.NEW;

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private PersonTestDataFactory personTestDataFactory;

    private Order createDefaultPerson() {
        Order order = new Order();
        order.setState(TEST_STATE);

        Person person = personTestDataFactory.getAndSavePerson();
        order.setPerson(person);
        return order;
    }

    public Order getAndSaveOrder() {
        Order order = createDefaultPerson();
        orderRepository.save(order);
        return order;
    }

    public void saveOrder() {
        Order order = createDefaultPerson();
        orderRepository.save(order);
    }

    public void saveOrder(State state, Person person) {
        Order order = new Order();
        order.setState(state == null ? TEST_STATE : state);
        order.setPerson(person == null ? personTestDataFactory.getAndSavePerson() : person);
        orderRepository.save(order);
    }

    public void saveOrder(Order order) {
        if(order.getState() == null) order.setState(TEST_STATE);
        if(order.getPerson() == null) order.setPerson(personTestDataFactory.getAndSavePerson());
        orderRepository.save(order);
    }
}
