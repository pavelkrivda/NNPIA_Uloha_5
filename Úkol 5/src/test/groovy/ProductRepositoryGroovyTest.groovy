import com.example.demo.datafactory.ProductTestDataFactory
import com.example.demo.datafactory.SupplierTestDataFactory
import com.example.demo.entity.Product
import com.example.demo.repository.ProductRepository
import org.assertj.core.api.Assertions
import org.junit.Assert
import org.junit.jupiter.api.Test
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import org.springframework.context.annotation.Import
import org.springframework.test.context.junit4.SpringRunner

import static org.hamcrest.MatcherAssert.assertThat
import static org.hamcrest.Matchers.hasSize

@RunWith(SpringRunner.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Import([ProductTestDataFactory.class, SupplierTestDataFactory.class])
public class ProductRepositoryGroovyTest {

    @Autowired
    private ProductRepository productRepository

    @Autowired
    private ProductTestDataFactory productTestDataFactory

    @Test
    private void saveProductTest() {
        Product product = new Product(name: ProductTestDataFactory.TEST_PRODUCT, description: ProductTestDataFactory.TEST_DESCRIPTION, supplier: ProductTestDataFactory.TEST_SUPPLIER);
        productTestDataFactory.saveProduct(product)

        List<Product> all = productRepository.findAll()
        assertThat(all, hasSize(1))

        Product readFromDatabase = productRepository.findById(all.get(0).getId()).get();
        Assertions.assertThat(readFromDatabase.getName()).isEqualTo(TEST_PRODUCT);
        Assertions.assertThat(readFromDatabase.getDescription()).isEqualTo(TEST_DESCRIPTION);
        Assertions.assertThat(readFromDatabase.getSupplier().getName()).isEqualTo(TEST_SUPPLIER);
    }

    @Test
    private void deleteProductTest() {
        Product product = new Product(name: TEST_PRODUCT, description: TEST_DESCRIPTION, supplier: TEST_SUPPLIER);
        productTestDataFactory.saveProduct(product)

        List<Product> all = productRepository.findAll()
        assertThat(all, hasSize(1))

        productRepository.delete(product)

        all = productRepository.findAll()
        assertThat(all, hasSize(0))
    }

    @Test
    private void findProductByNameContainsTest() {
        Product product = new Product(name: TEST_PRODUCT, description: TEST_DESCRIPTION, supplier: TEST_SUPPLIER);
        productTestDataFactory.saveProduct(product)

        Product findProduct = productRepository.findProductByNameContains(product.getName())
        Assert.assertNotNull(findProduct)
    }

    @Test
    private void findProductByIdBetweenTest() {
        Product productOne = new Product(name: TEST_PRODUCT, description: TEST_DESCRIPTION, supplier: TEST_SUPPLIER);
        productTestDataFactory.saveProduct(productOne)
        Product productTwo = new Product(name: TEST_PRODUCT, description: TEST_DESCRIPTION, supplier: TEST_SUPPLIER);
        productTestDataFactory.saveProduct(productTwo)
        Product productThree = new Product(name: TEST_PRODUCT, description: TEST_DESCRIPTION, supplier: TEST_SUPPLIER);
        productTestDataFactory.saveProduct(productThree)

        List<Product> findProduct = productRepository.findProductByIdBetween(productOne.getId(), productThree.getId())
        Assertions.assertThat(findProduct.size()).isEqualTo(3)
    }
}
