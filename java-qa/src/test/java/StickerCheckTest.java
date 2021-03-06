import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import java.util.List;


import static org.junit.Assert.assertEquals;

public class StickerCheckTest {
    public WebDriver driver;

    @Before
    public void start() {
        driver = new ChromeDriver();
    }


    @Test
    public void stickerCheck() {
        driver.get("http://localhost/litecart/en/");
        //создаем список из изображений товара
        List<WebElement> list = driver.findElements(By.cssSelector("li.product"));
        //перебираем каждый товар в цикле
        for (WebElement i: list) {
            //создаем список из стикеров на изображении товара
            List<WebElement> sticker = i.findElements(By.cssSelector("div.sticker"));
            //сравниваем список стикеров с 1
            assertEquals(1, sticker.size());
        }
    }

    @After
    public void stop() {
        driver.quit();
        driver = null;
    }
}
