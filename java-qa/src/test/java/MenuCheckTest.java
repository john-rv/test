import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import java.util.List;
import java.util.concurrent.TimeUnit;

import static org.junit.Assert.assertTrue;

public class MenuCheckTest {
    private WebDriver driver;
    public boolean areElementsPresents(By locator) {
        return driver.findElements(locator).size() > 0;
    }

    @Before
    public void start() {
        driver = new ChromeDriver();
    }

    @Test
    public void menuCheck() {
        driver.get("http://localhost/litecart/admin/login.php");
        driver.findElement(By.name("username")).sendKeys("admin");
        driver.findElement(By.name("password")).sendKeys("admin");
        driver.findElement(By.name("login")).click();

        //создаем список элементов из меню
        List<WebElement> list = driver.findElements(By.id("app-"));

        //перебираем каждый элемент меню в цикле
        for (int i = 0; i < list.size(); i++) {
            //создаем список элементов в меню, чтобы сделать клик
            List<WebElement> list1 = driver.findElements(By.id("app-"));
            //выбираем элемент списка и кликаем
            list1.get(i).click();
            //проверяем наличие заголовка
            assertTrue(areElementsPresents(By.tagName("h1")));
            //создаем список элементов подменю
            List<WebElement> sublist = driver.findElements(By.cssSelector("li#app-.selected li"));
            //перебираем каждый элемент подменю в цикле
            for (int k = 0; k < sublist.size(); k++) {
                //создаем список элементов из подменю, чтобы сделать клик
                List<WebElement> sublist1 = driver.findElements(By.cssSelector("li#app-.selected li"));
                //клик по подменю
                sublist1.get(k).click();
                //проверка наличия заголовка
                assertTrue(areElementsPresents(By.tagName("h1")));
            }
        }
    }

    @After
    public void stop() {
        driver.quit();
        driver = null;
    }
}
