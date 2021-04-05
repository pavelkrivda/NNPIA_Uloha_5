package com.example.demo.ui;

import com.example.demo.datafactory.Creator
import com.example.demo.entity.Product;
import com.example.demo.repository.ProductRepository
import com.example.demo.repository.SupplierRepository;
import org.junit.Assert;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@Import(Creator.class)
public class ProductUIGroovyTest {

    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private SupplierRepository supplierRepository;

    @Autowired
    private Creator creator;

    private WebDriver driver;

    @BeforeAll
    public static void setupWebdriverChromeDriver()  {
        URL url = TestImplementation.class.getResource("/chromedriver");
        String chromeDriverPaht = null;
        try {
            chromeDriverPaht = URLDecoder.decode(url.getFile(), "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        System.setProperty("webdriver.chrome.driver", chromeDriverPaht);
    }

    @BeforeEach
    public void setup() {
        driver = new ChromeDriver();

        productRepository.deleteAll();
        supplierRepository.deleteAll();
    }

    @AfterEach
    public void teardown() {
        if (driver != null) {
            driver.quit();
        }

        productRepository.deleteAll();
        supplierRepository.deleteAll();
    }

    @Test
    public void productList() {

        creator.saveEntities(
                new Product(name: "Product 1"),
                new Product(name: "Product 2"),
                new Product(name: "Product 3"),
        )

        driver.get("http://localhost:8080/");
        Assert.assertEquals(1, driver.findElements(By.xpath("//h3[text()='Product 1']")).size());
        Assert.assertEquals(1, driver.findElements(By.xpath("//h3[text()='Product 2']")).size());
        Assert.assertEquals(1, driver.findElements(By.xpath("//h3[text()='Product 3']")).size());
    }
}
