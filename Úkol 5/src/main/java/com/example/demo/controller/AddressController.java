package com.example.demo.controller;

import com.example.demo.dto.AddOrEditAddressDto;
import com.example.demo.entity.Address;
import com.example.demo.repository.AddressRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller()
public class AddressController {

    @Autowired
    private AddressRepository addressRepository;

    @ExceptionHandler(RuntimeException.class)
    public String handleException() {
        return "error";
    }

    @GetMapping("/address")
    public String showAllAddress(Model model) {
        model.addAttribute("addresses", addressRepository.findAll());
        return "address/address-list";
    }

    @GetMapping("/address-detail/{id}")
    public String showAddressDetail(@PathVariable(required = false) Long id, Model model) {
        model.addAttribute("address", addressRepository.findById(id).get());
        return "address/address-detail";
    }

    @GetMapping(value = {"/address-form", "/address-form/{id}"})
    public String showAddressForm(@PathVariable(required = false) Long id, Model model) {

        if (id != null) {
            Address address = addressRepository.findById(id).orElse(new Address());

            AddOrEditAddressDto dto = new AddOrEditAddressDto();
            dto.setId(address.getId());
            dto.setCity(address.getCity());
            dto.setState(address.getState());
            dto.setPostalCode(address.getPostalCode());

            model.addAttribute("address", dto);
        } else {
            model.addAttribute("address", new AddOrEditAddressDto());
        }

        return "address/address-form";
    }

    @PostMapping("/address-form-process")
    public String addAddressProcess(AddOrEditAddressDto addOrEditAddressDto) {
        Address address = new Address();
        address.setId(addOrEditAddressDto.getId());
        address.setCity(addOrEditAddressDto.getCity());
        address.setState(addOrEditAddressDto.getState());
        address.setPostalCode(addOrEditAddressDto.getPostalCode());

        boolean exist = addressRepository.existsAddressByCityAndStateAndPostalCode(
                address.getCity(), address.getState(), address.getPostalCode());

        if (!exist) {
            addressRepository.save(address);
        }

        return "redirect:/address";
    }

    @GetMapping("/address-remove/{id}")
    public String addressRemove(@PathVariable(required = false) Long id) {

        if (id != null) {
            Address address = addressRepository.findById(id).get();
            addressRepository.delete(address);
        }

        return "redirect:/address";
    }
}
