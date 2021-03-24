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

    private static final String TEST_PRODUCT_NAME = "Auto";
    private static final String TEST_DESCRIPTION = "Popis";

    @Autowired
    private ProductRepository productRepository;

    @Test
    public void saveProductTest() {
        Product product = new Product();
        product.setName(TEST_PRODUCT_NAME);
        product.setDescription(TEST_DESCRIPTION);
        productRepository.save(product);

        List<Product> all = productRepository.findAll();
        assertThat(all, hasSize(1));

        Product productFromDatabase = productRepository.findById(product.getId()).get();
        Assertions.assertThat(productFromDatabase.getName()).isEqualTo(TEST_PRODUCT_NAME);
        Assertions.assertThat(productFromDatabase.getDescription()).isEqualTo(TEST_DESCRIPTION);
    }

    @Test
    public void deleteProductTest() {
        Product product = new Product();
        product.setName(TEST_PRODUCT_NAME);
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
        product.setName(TEST_PRODUCT_NAME);
        productRepository.save(product);

        Product findProduct = productRepository.findProductByNameContains(TEST_PRODUCT_NAME);
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

        List<Product> findProduct = productRepository.findProductByIdBetween(productsOne.getId(), productsThree.getId());
        Assertions.assertThat(findProduct.size()).isEqualTo(3);
    }
}
