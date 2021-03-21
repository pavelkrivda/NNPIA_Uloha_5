package com.example.demo.controller;

import com.example.demo.dto.AddOrEditProductDto;
import com.example.demo.entity.Product;
import com.example.demo.repository.ProductRepository;
import com.example.demo.service.FileServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class ProductController {

    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private FileServiceImpl fileServiceImpl;

    @ExceptionHandler(RuntimeException.class)
    public String handleException(){
        return "error";
    }

    @GetMapping("/")
    public String showAllProducts(Model model){
        model.addAttribute("productList", productRepository.findAll());
        return "product-list";
    }

    @GetMapping("/product-detail/{id}")
    public String showProductDetail(@PathVariable(required = false) Long id, Model model){
        model.addAttribute("product", productRepository.findById(id).get());
        return "product-detail";
    }

    @GetMapping(value = {"/product-form", "/product-form/{id}"})
    public String showProductForm(@PathVariable(required = false) Long id, Model model){

        if(id != null){
            Product product = productRepository.findById(id).orElse(new Product());

            AddOrEditProductDto dto = new AddOrEditProductDto();
            dto.setId(product.getId());
            dto.setName(product.getName());
            dto.setDescription(product.getDescription());

            model.addAttribute("product", dto);
        }else{
            model.addAttribute("product", new AddOrEditProductDto());
        }

        return "product-form";
    }

    @PostMapping("/product-form-process")
    public String addProductProcess(AddOrEditProductDto addOrEditProductDto){
        Product product = new Product();
        product.setId(addOrEditProductDto.getId());
        product.setName(addOrEditProductDto.getName());
        product.setDescription(addOrEditProductDto.getDescription());

        String fileName = fileServiceImpl.upload(addOrEditProductDto.getImage());
        product.setPathToImage(fileName);
        productRepository.save(product);
        return "redirect:/";
    }
}
