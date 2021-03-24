package com.example.demo;

import com.example.demo.datafactory.Creator;
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

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasSize;

@RunWith(SpringRunner.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Import([Creator.class])
public class ProductRepositoryGroovyTest {

    public static final String TEST_PRODUCT = "My product";
    public static final String TEST_DESCRIPTION = "Test description";
    public static final String TEST_SUPPLIER = "Test name";

    @Autowired
    private Creator creator;

    @Autowired
    private ProductRepository productRepository;

    @Test
    public void saveProductTest() {
        Product product = new Product(name: TEST_PRODUCT, description: TEST_DESCRIPTION);
        creator.save(product);

        List<Product> all = productRepository.findAll();
        assertThat(all, hasSize(1));

        Product readFromDatabase = productRepository.findById(product.getId()).get();
        Assertions.assertThat(readFromDatabase.name).isEqualTo(TEST_PRODUCT);
        Assertions.assertThat(readFromDatabase.description).isEqualTo(TEST_DESCRIPTION);
        Assertions.assertThat(readFromDatabase.getSupplier().name).isEqualTo(TEST_SUPPLIER);
    }

    @Test
    public void deleteProductTest() {
        Product product = new Product(name: TEST_PRODUCT, description: TEST_DESCRIPTION);
        creator.save(product);

        List<Product> all = productRepository.findAll();
        assertThat(all, hasSize(1));

        productRepository.delete(product);

        all = productRepository.findAll();
        assertThat(all, hasSize(0));
    }

    @Test
    public void findProductByNameContainsTest() {
        Product product = new Product(name: TEST_PRODUCT, description: TEST_DESCRIPTION);
        creator.save(product);

        Product findProduct = productRepository.findProductByNameContains(product.getName());
        Assert.assertNotNull(findProduct);
    }

    @Test
    public void findProductByIdBetweenTest() {
        Product productOne = new Product(name: TEST_PRODUCT, description: TEST_DESCRIPTION);
        creator.save(productOne);
        Product productTwo = new Product(name: TEST_PRODUCT, description: TEST_DESCRIPTION);
        creator.save(productTwo);
        Product productThree = new Product(name: TEST_PRODUCT, description: TEST_DESCRIPTION);
        creator.save(productThree);

        List<Product> findProduct = productRepository.findProductByIdBetween(productOne.getId(), productThree.getId());
        Assertions.assertThat(findProduct.size()).isEqualTo(3);
    }
}
