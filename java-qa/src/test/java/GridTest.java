import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.ie.InternetExplorerOptions;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.net.MalformedURLException;
import java.net.URL;

public class GridTest {
    private WebDriver driver;
    private WebDriverWait wait;


    @Before
    public void start() throws MalformedURLException {
        DesiredCapabilities caps = new DesiredCapabilities();
        caps.setBrowserName("internet explorer");
       // ChromeOptions options = new ChromeOptions();
        InternetExplorerOptions options = new InternetExplorerOptions();
        options.merge(caps);
        driver = new RemoteWebDriver(
                new URL("http://192.168.0.102:4444/wd/hub"),options
        );

    }

    @Test
    public void ya() {
        driver.get("http://ya.ru/");
        driver.findElement(By.cssSelector("input.input__control")).sendKeys("webdriver", Keys.ENTER);

    }
    @After
    public void stop(){
        driver.quit();
        driver = null;
    }
}
