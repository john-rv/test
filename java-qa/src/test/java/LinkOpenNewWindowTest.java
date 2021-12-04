import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;
import java.util.Random;
import java.util.Set;

public class LinkOpenNewWindowTest {
    public WebDriver driver;
    public WebDriverWait wait;

    @Before
    public void start() {
        driver = new FirefoxDriver();
        wait = new WebDriverWait(driver, 10);
        driver.get("http://localhost/litecart/admin");
        driver.findElement(By.name("username")).sendKeys("admin");
        driver.findElement(By.name("password")).sendKeys("admin");
        driver.findElement(By.name("login")).click();
    }

    @Test
    public void LinkOpenNewWindow() {
        driver.findElement(By.linkText("Countries")).click();
        //кликаем по рандомной стране
        List<WebElement> countries = driver.findElements(By.cssSelector("tr.row > td:nth-child(5) > a"));
        Random random = new Random();
        countries.get(random.nextInt(countries.size())).click();
        //получаем значение текущего окна
        String mainWindow = driver.getWindowHandle();
        //находим все ссылки с иконкой
        List<WebElement> links = driver.findElements(By.cssSelector("form a[target = \"_blank\"]"));
        //проверяем каждую ссылку
        for (WebElement i : links) {
            //кликаем по ссылке
            i.click();
            //ждем пока количество окон будет равно 2
            wait.until(ExpectedConditions.numberOfWindowsToBe(2));
            //получаем значения окон
            Set<String> windows = driver.getWindowHandles();
            //удаляем значение главного окна
            windows.remove(mainWindow);
            //получаем значение нового окна
            String newWindow = windows.iterator().next();
            //переключаемся в новое окно
            driver.switchTo().window(newWindow);
            //закрываем новое окно
            driver.close();
            //переключаемся в главное окно
            driver.switchTo().window(mainWindow);
        }
    }

    @After
    public void stop() {
        driver.quit();
        driver = null;
    }
}
