package com.example.demo.ui

import com.example.demo.datafactory.Creator
import com.example.demo.entity.Person
import com.example.demo.repository.AddressRepository
import com.example.demo.repository.PersonRepository
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
public class PersonUIGroovyTest {

    @Autowired
    private PersonRepository personRepository;
    @Autowired
    private AddressRepository addressRepository;

    @Autowired
    private Creator creator;

    private WebDriver driver;

    @BeforeAll
    public static void setupWebdriverChromeDriver() {
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
    public void personList() {

        creator.saveEntities(
                new Person(firstName: "Pepa"),
                new Person(firstName: "Karel"),
                new Person(firstName: "Olga"),
        )

        driver.get("http://localhost:8080/person");
        Assert.assertTrue( driver.findElements(By.xpath("/html/body/section/table/tbody/tr/td[2]/span[text()='Pepa']")).size() >= 1);
        Assert.assertTrue( driver.findElements(By.xpath("/html/body/section/table/tbody/tr/td[2]/span[text()='Karel']")).size() >= 1);
        Assert.assertTrue( driver.findElements(By.xpath("/html/body/section/table/tbody/tr/td[2]/span[text()='Olga']")).size() >= 1);
    }
}
