package com.example.demo.ui;

import com.example.demo.repository.AddressRepository;
import com.example.demo.repository.PersonRepository;
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
public class PersonUITest {

    @Autowired
    private PersonRepository personRepository;
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

        personRepository.deleteAll();
        addressRepository.deleteAll();
    }

    @AfterEach
    public void teardown() {
        if (driver != null) {
            driver.quit();
        }

        personRepository.deleteAll();
        addressRepository.deleteAll();
    }

    @Test
    public void addPerson() {
        driver.get("http://localhost:8080/person-form");
        driver.findElement(By.id("firstName")).sendKeys("Pepa");
        driver.findElement(By.id("lastName")).sendKeys("Novák");
        driver.findElement(By.id("age")).sendKeys("54");
        driver.findElement(By.id("city")).sendKeys("Praha");
        driver.findElement(By.id("state")).sendKeys("Czech Republic");
        driver.findElement(By.id("postalCode")).sendKeys("43814");
        driver.findElement(By.xpath("//Button[@type='submit']")).submit();

        Assert.assertTrue( driver.findElements(By.xpath("/html/body/section/table/tbody/tr/td[2]/span[text()='Pepa']")).size() >= 1);
        Assert.assertTrue( driver.findElements(By.xpath("/html/body/section/table/tbody/tr/td[3]/span[text()='Novák']")).size()>= 1);
    }
}
