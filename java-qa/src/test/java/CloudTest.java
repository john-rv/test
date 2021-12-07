import net.bytebuddy.asm.Advice;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

import static java.util.Comparator.*;
import static org.openqa.selenium.support.ui.ExpectedConditions.*;

public class CloudTest {
   private WebDriver driver;
   private WebDriverWait wait;

   //запуск на https://saucelabs.com/
   @Before
   public void start() throws MalformedURLException {
      DesiredCapabilities caps = new DesiredCapabilities();
      caps.setBrowserName("chrome");
      ChromeOptions options = new ChromeOptions();
      options.merge(caps);
      driver = new RemoteWebDriver(
              new URL("https://oauth-evr2812-aacb9:e3042ad9-1604-4fc7-8102-85b36261c006@ondemand.eu-central-1.saucelabs.com:443/wd/hub"),options
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
