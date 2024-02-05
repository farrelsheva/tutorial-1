package id.ac.ui.cs.advprog.eshop.functional;

import io.github.bonigarcia.seljup.SeleniumJupiter;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.openqa.selenium.By;
import org.openqa.selenium.chrome.ChromeDriver;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;

@SpringBootTest(webEnvironment = RANDOM_PORT)
@ExtendWith(SeleniumJupiter.class)
public class HomePageFunctionalTest {
    //Port number assigned to the application during testing
    //set automatically by Spring Framework's test context
    @LocalServerPort
    private int serverPort;

    //base URL for testing. Default to {@code http://localhost}
    @Value("${app.baseUrl:http://localhost}")
    private String testBaseUrl;

    private String baseUrl;

    @BeforeEach
    void setupTest(){
        baseUrl = String.format("%s:%d", testBaseUrl, serverPort);
    }

    //Snake case is used for testing names in java, Due to longer names
    @Test
    void PageTitle_isCorrect(ChromeDriver driver)throws Exception{
        //TODO
        driver.get(baseUrl);
        String pageTitle = driver.getTitle();

        assertEquals("ADV Shop",pageTitle);

    }

    @Test
    void welcomeMessage_homePage_isCorrect(ChromeDriver driver)throws Exception{
        //TODO
        driver.get(baseUrl);
        String welcomeMessage = driver.findElement(By.id("welcomeMessage")).getText();

        assertEquals("Welcome",welcomeMessage);
    }
}
