package ntiteam.test.planetOverlord.SeleniumTest.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;

public class HomePage {

    @FindBy(how = How.CLASS_NAME, using = "letsstart_div")
    public WebElement linkToNewPage;

    public void ClickToNewPage(){
        WebDriver driver = new ChromeDriver();

        driver.get("http://localhost:8080/");

        linkToNewPage.click();
    }
}
