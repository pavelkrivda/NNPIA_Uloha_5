package com.example.demo.datafactory;

import com.example.demo.entity.Product;
import com.example.demo.entity.Supplier;
import com.example.demo.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ProductTestDataFactory {

    public static final String TEST_PRODUCT = "Test product";
    public static final String TEST_DESCRIPTION = "Test description";

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private SupplierTestDataFactory supplierTestDataFactory;

    private Product createDefaultProduct() {
        Product product = new Product();
        product.setName(TEST_PRODUCT);
        product.setDescription(TEST_DESCRIPTION);
        return product;
    }

    private void saveProductWithDefaultSupplier(Product product) {
        Supplier supplier = supplierTestDataFactory.saveSupplier();
        product.setSupplier(supplier);
        productRepository.save(product);
    }

    public Product getAndSaveOrder() {
        Product product = createDefaultProduct();
        saveProductWithDefaultSupplier(product);
        return product;
    }

    public void saveProduct() {
        Product product = createDefaultProduct();
        saveProductWithDefaultSupplier(product);
    }

    public void saveProduct(String name, String description) {
        Product product = new Product();
        product.setName(name == null ? TEST_PRODUCT : name);
        product.setDescription(description == null ? TEST_DESCRIPTION : description);
        saveProductWithDefaultSupplier(product);
    }

    public void saveProduct(Product product) {
        if(product.getName() == null) product.setName(TEST_PRODUCT);
        if(product.getDescription() == null) product.setDescription(TEST_DESCRIPTION);
        saveProductWithDefaultSupplier(product);
    }
}
