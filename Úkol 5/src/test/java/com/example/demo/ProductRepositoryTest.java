package com.example.demo;


import com.example.demo.datafactory.ProductTestDataFactory;
import com.example.demo.datafactory.SupplierTestDataFactory;
import com.example.demo.entity.Product;
import com.example.demo.repository.ProductRepository;
import org.assertj.core.api.Assertions;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasSize;

@RunWith(SpringRunner.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Import({ProductTestDataFactory.class, SupplierTestDataFactory.class})
public class ProductRepositoryTest {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private ProductTestDataFactory productTestDataFactory;

    @Test
    public void saveProductTest() {
        productTestDataFactory.saveProduct();
        List<Product> all = productRepository.findAll();
        assertThat(all, hasSize(1));

        Product readFromDatabase = productRepository.findById(all.get(0).getId()).get();
        Assertions.assertThat(readFromDatabase.getName()).isEqualTo(ProductTestDataFactory.TEST_PRODUCT);
        Assertions.assertThat(readFromDatabase.getDescription()).isEqualTo(ProductTestDataFactory.TEST_DESCRIPTION);
        Assertions.assertThat(readFromDatabase.getSupplier().getName()).isEqualTo(SupplierTestDataFactory.TEST_SUPPLIER);
    }

    @Test
    public void deleteProductTest() {
        productTestDataFactory.saveProduct();

        List<Product> all = productRepository.findAll();
        assertThat(all, hasSize(1));

        productRepository.delete(all.get(0));

        all = productRepository.findAll();
        assertThat(all, hasSize(0));
    }

    @Test
    public void findProductByNameContainsTest() {
        productTestDataFactory.saveProduct();

        Product findProduct = productRepository.findProductByNameContains("Test product");
        Assert.assertNotNull(findProduct);
    }

    @Test
    public void findProductByIdBetweenTest() {
        productTestDataFactory.saveProduct();
        productTestDataFactory.saveProduct();
        productTestDataFactory.saveProduct();

        List<Product> all = productRepository.findAll();
        long id = all.get(0).getId();

        List<Product> findProduct = productRepository.findProductByIdBetween(id, id + 1);
        Assertions.assertThat(findProduct.size()).isEqualTo(2);
    }
}
