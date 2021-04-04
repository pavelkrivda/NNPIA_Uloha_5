package com.example.demo.ui;

import com.example.demo.repository.ProductRepository;
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

import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLDecoder;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
public class ProductUITest {

    @Autowired
    private ProductRepository productRepository;

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
    }

    @AfterEach
    public void teardown() {
        if (driver != null) {
            driver.quit();
        }
    }

    @Test
    public void addProduct() {
        driver.get("http://localhost:8080/product-form");
        driver.findElement(By.id("productName")).sendKeys("Kniha");
        driver.findElement(By.id("description")).sendKeys("Popis knihy");
        driver.findElement(By.id("image")).sendKeys(
                "/home/pavlas123/Programování/Java/Internetové aplikace git/Úkol 5/src/test/resources/book.png"
        );
        driver.findElement(By.id("supplier")).sendKeys("Dodavatel");
        driver.findElement(By.xpath("//Button[@type='submit']")).submit();

        Assert.assertEquals(1, driver.findElements(By.xpath("//h2[text()='Product list']")).size());
        Assert.assertEquals(1, driver.findElements(By.xpath("//h2[text()='Product list']")).size());
    }
}
