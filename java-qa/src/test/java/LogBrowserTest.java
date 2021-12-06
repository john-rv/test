import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import java.util.ArrayList;
import java.util.List;

public class LogBrowserTest {
    public WebDriver driver;

    @Before
    public void start() {
        driver = new ChromeDriver();
        driver.get("http://localhost/litecart/admin/login.php");
        driver.findElement(By.name("username")).sendKeys("admin");
        driver.findElement(By.name("password")).sendKeys("admin");
        driver.findElement(By.name("login")).click();
    }

    @Test
    public void logBrowser() {
        //переходим на страницу товаров
        driver.findElement(By.linkText("Catalog")).click();
        driver.findElement(By.linkText("Rubber Ducks")).click();
        //получаем список элементов с названиями товаров
        List<WebElement> product = driver.findElements(By.cssSelector("td:nth-child(3) > a[href*=\"product\"]"));
        //создаем список для названий товаров и заполняем его
        List<String> productName = new ArrayList<>();
        for (WebElement i : product) {
            productName.add(i.getText());
        }
        //переходим на страницу каждого товара и проверяем логи браузера
        for (String k : productName) {
            driver.findElement(By.linkText(k)).click();
            driver.manage().logs().get("browser").getAll().forEach(l -> System.out.println(l));
            driver.findElement(By.name("cancel")).click();
        }
    }

    @After
    public void stop() {
        driver.quit();
        driver = null;
    }
}
