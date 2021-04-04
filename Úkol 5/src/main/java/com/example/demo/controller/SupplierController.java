package com.example.demo.controller;

import com.example.demo.dto.AddOrEditSupplierDto;
import com.example.demo.entity.Supplier;
import com.example.demo.repository.ProductRepository;
import com.example.demo.repository.SupplierRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class SupplierController {

    @Autowired
    private SupplierRepository supplierRepository;

    @Autowired
    private ProductRepository productRepository;

    @ExceptionHandler(RuntimeException.class)
    public String handleException() {
        return "error";
    }

    @GetMapping("/supplier")
    public String showAllSuppliers(Model model) {
        model.addAttribute("supplierList", supplierRepository.findAll());
        return "supplier/supplier-list";
    }

    @GetMapping("/supplier-detail/{id}")
    public String showSupplierDetail(@PathVariable(required = false) Long id, Model model) {
        model.addAttribute("supplier", supplierRepository.findById(id).get());
        return "supplier/supplier-detail";
    }

    @GetMapping(value = {"/supplier-form", "/supplier-form/{id}"})
    public String showSupplierForm(@PathVariable(required = false) Long id, Model model) {

        if (id != null) {
            Supplier supplier = supplierRepository.findById(id).orElse(new Supplier());

            AddOrEditSupplierDto dto = new AddOrEditSupplierDto();
            dto.setId(supplier.getId());
            dto.setName(supplier.getName());

            model.addAttribute("supplier", dto);
        } else {
            model.addAttribute("supplier", new AddOrEditSupplierDto());
        }

        return "supplier/supplier-form";
    }

    @PostMapping("/supplier-form-process")
    public String addSupplierProcess(AddOrEditSupplierDto addOrEditSupplierDto) {
        Supplier supplier = new Supplier();
        supplier.setId(addOrEditSupplierDto.getId());
        supplier.setName(addOrEditSupplierDto.getName());

        if (!supplierRepository.existsByName(supplier.getName()))
            supplierRepository.save(supplier);

        return "redirect:/supplier";
    }

    @GetMapping("/supplier-remove/{id}")
    public String supplierRemove(@PathVariable(required = false) Long id) {

        if (id != null) {
            Supplier supplier = supplierRepository.findById(id).get();

            if (productRepository.existsProductsBySupplierId(supplier.getId())) {
                // TODO vypsat chybovou zprávyu
            } else {
                supplierRepository.delete(supplier);
            }
        }

        return "redirect:/supplier";
    }
}
