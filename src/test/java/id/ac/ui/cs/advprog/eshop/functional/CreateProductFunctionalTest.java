package id.ac.ui.cs.advprog.eshop.functional;

import io.github.bonigarcia.seljup.SeleniumJupiter;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;


@SpringBootTest(webEnvironment = RANDOM_PORT)
@ExtendWith(SeleniumJupiter.class)
public class CreateProductFunctionalTest {
    //Port number assigned to the application during testing
    //set automatically by Spring Framework's test context
    @LocalServerPort
    private int serverPort;

    //base URL for testing. Default to {@code http://localhost}
    @Value("${app.baseUrl:http://localhost}")
    private String testBaseUrl;

    private String baseUrl;

    private String basePath = "/product/list";
    @BeforeEach
    void setupTest(){
        baseUrl = String.format("%s:%d%s", testBaseUrl, serverPort, basePath);
    }

    @Test
    void NavigateToCreatePage(ChromeDriver driver)throws Exception{
        driver.get(baseUrl);
        driver.findElement(By.id("createProduct")).click();

        String pageTitle = driver.getTitle();
        assertEquals("Create New Product",pageTitle);
    }

    @Test
    void CreateProduct(ChromeDriver driver)throws Exception {
        String InputName = "Test Product";
        String InputQuantity = "1000";

        driver.get(baseUrl);
        driver.findElement(By.id("createProduct")).click();

        driver.findElement(By.id("nameInput")).sendKeys(InputName);
        driver.findElement(By.id("quantityInput")).sendKeys(InputQuantity);
        driver.findElement(By.id("Submit")).click();

        String pageTitle = driver.getTitle();
        assertEquals("Product List", pageTitle);

        WebElement productName = driver.findElement(By.id("name-" + InputName));
        assertEquals(InputName, productName.getText());
        WebElement productQuantity = driver.findElement(By.id("quantity-" + InputName));
        assertEquals(InputQuantity, productQuantity.getText());
    }
}



