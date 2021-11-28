import com.google.common.collect.Ordering;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import java.util.*;
import static org.junit.Assert.assertTrue;

public class CountryCheckTest {
    private WebDriver driver;

    @Before
    public void start() {
        driver = new ChromeDriver();
        driver.get("http://localhost/litecart/admin/?app=countries&doc=countries");
        driver.findElement(By.name("username")).sendKeys("admin");
        driver.findElement(By.name("password")).sendKeys("admin");
        driver.findElement(By.name("login")).click();
    }

    @Test
    public void countryCheck() {
        //создаем список с элементами стран
        List<WebElement> country = driver.findElements(By.cssSelector(".dataTable td:nth-child(5)"));
        //создаем список, который будет содержать названия стран
        List<String> nameCountry = new ArrayList<>();
        //через цикл заполняем список названиями стран
        for (WebElement i : country) {
            nameCountry.add(i.getText());
         }
        //проверяем отсортирован ли список по алфавиту
        Assert.assertTrue("Список стран не отосортирован по алфавиту", Ordering.natural().isOrdered(nameCountry));
    }

    @Test
    public void zoneCountry() {
        //создаем список с элементами стран
        List<WebElement> country = driver.findElements(By.cssSelector(".dataTable td:nth-child(5)"));
        //создаем список с элементами зон
        List<WebElement> zoneCountry = driver.findElements(By.cssSelector(".dataTable td:nth-child(6)"));
        //создаем список, который будет содержать названия стран
        List<String> nameCountry = new ArrayList<>();
        //через цикл заполняем список названиями стран
        for (WebElement i : country) {
            nameCountry.add(i.getText());
        }
        //Создаем список для конвертации текстового значения количества зон в числовое
        List<Integer> countZone = new ArrayList<>();
        for (WebElement i : zoneCountry) {
            countZone.add(Integer.valueOf(i.getText()));
        }
        //сравниваем количество зон с 0
        for (int i = 0; i < countZone.size(); i++) {
            if (countZone.get(i) > 0) {
                //находим на странице страну по названию и кликаем на неее
                driver.findElement(By.linkText(nameCountry.get(i))).click();
                //создаем список с элементами зон
                List<WebElement> zone = driver.findElements(By.xpath("//*[@id=\"table-zones\"]//td[3]/text()/.."));
                //создаем список, который будет содержать названия зон
                List<String> zoneName = new ArrayList<>();
                //через цикл заполняем список названиями зон
                for (WebElement n : zone) {
                    zoneName.add(n.getText());
                }
                //проверяем отсортирован ли список зон по алфавиту
                 assertTrue("Список зон не отсортирован по алфавиту", Ordering.natural().isOrdered(zoneName));
                //возвращаемся в меню стран
                driver.findElement(By.cssSelector("li#app-.selected")).click();
            }
        }
    }

    @Test
    public void geoZones() {
        driver.get("http://localhost/litecart/admin/?app=geo_zones&doc=geo_zones");
        //создаем список из ссылок которые содержат названия стран
        List<WebElement> country = driver.findElements(By.cssSelector(".dataTable td:nth-child(3)"));
        //создаем список, который будет содержать названия стран
        List<String> nameCountry = new ArrayList<>();
        //через цикл заполняем список названиями стран
        for (WebElement i : country) {
            nameCountry.add(i.getText());
        }
        //через цикл заходим в каждую страну
        for (String s : nameCountry) {
            driver.findElement(By.linkText(s)).click();
            //создаем список из элементов с зонами
            List<WebElement> zone = driver.findElements(By.cssSelector("#table-zones td:nth-child(3) option[selected]"));
            //создаем список, который будет содержать названия зон
            List<String> zoneName = new ArrayList<>();
            //через цикл заполняем список названиями зон
            for (WebElement n : zone) {
                zoneName.add(n.getText());
            }
            //проверяем отсортирован ли список по алфавиту
            assertTrue("Список зон не отсортирован по алфавиту", Ordering.natural().isOrdered(zoneName));
            //возвращаемся в меню гео зоны
            driver.findElement(By.cssSelector("li#app-.selected")).click();
        }
    }

    @After
    public void stop() {
        driver.quit();
        driver = null;
    }
}
