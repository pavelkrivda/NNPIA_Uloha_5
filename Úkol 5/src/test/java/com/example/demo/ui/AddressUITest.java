package com.example.demo.ui;

import com.example.demo.repository.AddressRepository;
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
public class AddressUITest {

    @Autowired
    private AddressRepository addressRepository;

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

        addressRepository.deleteAll();
    }

    @AfterEach
    public void teardown() {
        if (driver != null) {
            driver.quit();
        }
    }

    @Test
    public void addAdress() {
        driver.get("http://localhost:8080/address-form");
        driver.findElement(By.id("city")).sendKeys("Praha");
        driver.findElement(By.id("state")).sendKeys("Czech Republic");
        driver.findElement(By.id("postalCode")).sendKeys("43814");
        driver.findElement(By.xpath("//Button[@type='submit']")).submit();

        Assert.assertEquals(1, driver.findElements(By.xpath("/html/body/section/table/tbody/tr/td[2]/span[text()='Praha']")).size());
        Assert.assertEquals(1, driver.findElements(By.xpath("/html/body/section/table/tbody/tr/td[3]/span[text()='Czech Republic']")).size());
        Assert.assertEquals(1, driver.findElements(By.xpath("/html/body/section/table/tbody/tr/td[4]/span[text()='43814']")).size());
    }
}
