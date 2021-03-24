package com.example.demo.datafactory;

import com.example.demo.entity.Supplier;
import com.example.demo.repository.SupplierRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class SupplierTestDataFactory {

    public static final String TEST_SUPPLIER = "Test supplier";

    @Autowired
    private SupplierRepository supplierRepository;

    public Supplier saveSupplier() {
        Supplier supplier = new Supplier();
        supplier.setName(TEST_SUPPLIER);
        supplierRepository.save(supplier);
        return supplier;
    }
}
