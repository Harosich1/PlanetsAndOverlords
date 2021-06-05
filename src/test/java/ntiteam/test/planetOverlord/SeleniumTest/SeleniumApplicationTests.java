package ntiteam.test.planetOverlord.SeleniumTest;

import ntiteam.test.planetOverlord.SeleniumTest.pages.HomePage;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class SeleniumApplicationTests {

    @Test
    void navigateToHomePage() {
        HomePage homePage = new HomePage();
        homePage.ClickToNewPage();
    }
}
