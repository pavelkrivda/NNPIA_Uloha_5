package com.example.demo.dto;

import com.example.demo.entity.Address;
import com.example.demo.entity.Order;
import com.example.demo.entity.Person;

import javax.persistence.*;
import java.util.Set;

public class AddOrEditPersonDto {

    private Long id;

    private Address address;

    private String firstName;

    private String lastName;

    private Integer age;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }
}
