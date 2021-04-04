package com.example.demo.controller;

import com.example.demo.dto.AddOrEditPersonDto;
import com.example.demo.entity.Address;
import com.example.demo.entity.Person;
import com.example.demo.repository.AddressRepository;
import com.example.demo.repository.OrderRepository;
import com.example.demo.repository.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller()
public class PersonController {

    @Autowired
    private PersonRepository personRepository;
    @Autowired
    private AddressRepository addressRepository;
    @Autowired
    private OrderRepository orderRepository;

    @ExceptionHandler(RuntimeException.class)
    public String handleException() {
        return "error";
    }

    @GetMapping("/person")
    public String showAllPerson(Model model) {
        model.addAttribute("persons", personRepository.findAll());
        return "person/person-list";
    }

    @GetMapping("/person-detail/{id}")
    public String showPersonDetail(@PathVariable(required = false) Long id, Model model) {
        model.addAttribute("person", personRepository.findById(id).get());
        return "person/person-detail";
    }

    @GetMapping(value = {"/person-form", "/person-form/{id}"})
    public String showPersonForm(@PathVariable(required = false) Long id, Model model) {

        if (id != null) {
            Person person = personRepository.findById(id).orElse(new Person());

            AddOrEditPersonDto dto = new AddOrEditPersonDto();
            dto.setId(person.getId());
            dto.setFirstName(person.getFirstName());
            dto.setLastName(person.getLastName());
            dto.setAge(person.getAge());
            dto.setAddress(person.getAddress());

            model.addAttribute("person", dto);
        } else {
            model.addAttribute("person", new AddOrEditPersonDto());
        }

        return "person/person-form";
    }

    @PostMapping("/person-form-process")
    public String addPersonProcess(AddOrEditPersonDto addOrEditPersonDto) {
        Person person = new Person();
        person.setId(addOrEditPersonDto.getId());
        person.setFirstName(addOrEditPersonDto.getFirstName());
        person.setLastName(addOrEditPersonDto.getLastName());
        person.setAge(addOrEditPersonDto.getAge());

        Address address = addOrEditPersonDto.getAddress();
        boolean exist = addressRepository.existsAddressByCityAndStateAndPostalCode(
                address.getCity(), address.getState(), address.getPostalCode());

        if(!exist){
            addressRepository.save(address);
        }else{
            address = addressRepository.findAddressByCityAndStateAndPostalCode(
                    address.getCity(), address.getState(), address.getPostalCode());
        }

        person.setAddress(address);

        personRepository.save(person);
        return "redirect:/person";
    }

    @GetMapping("/person-remove/{id}")
    public String personRemove(@PathVariable(required = false) Long id) {

        if (id != null) {
            Person person = personRepository.findById(id).get();

            if (orderRepository.existsOrderByPerson_Id(person.getId())) {
                // TODO vypsat chybovou zprávyu
            } else {
                personRepository.delete(person);
            }
        }

        return "redirect:/person";
    }
}
