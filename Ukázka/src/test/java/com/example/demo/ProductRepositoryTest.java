package com.example.demo;


import com.example.demo.entity.Product;
import com.example.demo.repository.ProductRepository;
import org.assertj.core.api.Assertions;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasSize;

@RunWith(SpringRunner.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
//@SpringBootTest
class ProductRepositoryTest {

    @Autowired
    private ProductRepository productRepository;

    @Test
    public void saveProductTest() {
        Product product = new Product();
        product.setName("Auto");
        productRepository.save(product);

        List<Product> all = productRepository.findAll();
        assertThat(all, hasSize(1));
    }

    @Test
    public void deleteProductTest() {
        Product product = new Product();
        product.setName("Auto");
        productRepository.save(product);

        List<Product> all = productRepository.findAll();
        assertThat(all, hasSize(1));

        productRepository.delete(product);

        all = productRepository.findAll();
        assertThat(all, hasSize(0));
    }

    @Test
    public void findProductByNameContainsTest() {
        Product product = new Product();
        product.setName("Auto");
        productRepository.save(product);

        Product findProduct = productRepository.findProductByNameContains("Auto");
        Assert.assertNotNull(findProduct);
    }

    @Test
    public void findProductByIdBetweenTest() {
        Product productsOne = new Product();
        Product productsTwo = new Product();
        Product productsThree = new Product();

        productsOne.setName("Product one");
        productsTwo.setName("Product two");
        productsThree.setName("Product three");

        productRepository.save(productsOne);
        productRepository.save(productsTwo);
        productRepository.save(productsThree);

        List<Product> findProduct = productRepository.findProductByIdBetween(0L,1L);
        Assertions.assertThat(findProduct.size()).isEqualTo(2);
    }
}
