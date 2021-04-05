package com.example.demo.ui;

import com.example.demo.datafactory.Creator
import com.example.demo.entity.Address
import com.example.demo.repository.AddressRepository
import org.junit.Assert
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.openqa.selenium.By
import org.openqa.selenium.WebDriver
import org.openqa.selenium.chrome.ChromeDriver
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.context.annotation.Import

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@Import(Creator.class)
public class AddressUIGroovyTest {

    @Autowired
    private AddressRepository addressRepository;

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

        addressRepository.deleteAll();
    }

    @AfterEach
    public void teardown() {
        if (driver != null) {
            driver.quit();
        }

        addressRepository.deleteAll();
    }

    @Test
    public void addressList() {

        creator.saveEntities(
                new Address(city: "Praha"),
                new Address(city: "Brno"),
                new Address(city: "Ostrava")
        )

        driver.get("http://localhost:8080/address");
        Assert.assertEquals(1, driver.findElements(By.xpath("/html/body/section/table/tbody/tr/td[2]/span[text()='Praha']")).size());
        Assert.assertEquals(1, driver.findElements(By.xpath("/html/body/section/table/tbody/tr/td[2]/span[text()='Brno']")).size());
        Assert.assertEquals(1, driver.findElements(By.xpath("/html/body/section/table/tbody/tr/td[2]/span[text()='Ostrava']")).size());
    }
}
