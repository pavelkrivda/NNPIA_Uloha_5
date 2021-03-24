package com.example.demo;

import com.example.demo.datafactory.ProductTestDataFactory;
import com.example.demo.datafactory.SupplierTestDataFactory;
import com.example.demo.entity.Product;
import com.example.demo.repository.ProductRepository;
import com.example.demo.service.ShoppingCartService;
import com.example.demo.service.ShoppingCartServiceImpl;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Import([ProductTestDataFactory.class, SupplierTestDataFactory.class, ShoppingCartServiceImpl.class])
public class ShoppingCartGroovyTest {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private ShoppingCartService shoppingCartService;

    @Autowired
    private ProductTestDataFactory productTestDataFactory;

    @Test
    public void addOneToShoppingCart() {
        productTestDataFactory.saveProduct();

        List<Product> all = productRepository.findAll();
        Long productId = all.get(0).getId();
        shoppingCartService.add(productId);

        // počet prvků v košíku = 1
        Assertions.assertThat(shoppingCartService.getCart().size()).isEqualTo(1);

        // obsahuje právě vložený prvek
        Assertions.assertThat(shoppingCartService.getCart().containsKey(all.get(0))).isTrue();

        // obsahuje vložený prodkut v počtu = 1
        Assertions.assertThat(shoppingCartService.getCart().size()).isEqualTo(1);


        // Add
        shoppingCartService.add(productId);
        Assertions.assertThat(shoppingCartService.getCart().get(all.get(0))).isEqualTo(2);

        shoppingCartService.add(productId);
        Assertions.assertThat(shoppingCartService.getCart().get(all.get(0))).isEqualTo(3);


        // Remove
        shoppingCartService.remove(productId);
        Assertions.assertThat(shoppingCartService.getCart().get(all.get(0))).isEqualTo(2);

        shoppingCartService.remove(productId);
        Assertions.assertThat(shoppingCartService.getCart().get(all.get(0))).isEqualTo(1);

        shoppingCartService.remove(productId);
        Assertions.assertThat(shoppingCartService.getCart().containsKey(all.get(0))).isFalse();
    }
}
