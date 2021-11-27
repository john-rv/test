import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.junit.Assert.assertEquals;

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
        //создаем список из ссылок которые содержат названия стран
        List<WebElement> country = driver.findElements(By.cssSelector(".dataTable td:nth-child(5)"));
        //создаем два списка, в одном из которых произведем сортировку по алфавиту
        List<String> nameCountry = new ArrayList<>();
        List<String> nameCountrySort = new ArrayList<>();
        //через цикл заполняем списки названиями стран
        for (WebElement i : country) {
            nameCountry.add(i.getText());
            nameCountrySort.add(i.getText());
        }
        //сортируем список по алфавиту
        Collections.sort(nameCountrySort);
        //сравниваем отсортированный список со списком который был получен со страницы
        assertEquals(nameCountrySort, nameCountry);
    }

    @Test
    public void zoneCountry() {
        //создаем список из ссылок которые содержат названия стран
        List<WebElement> country = driver.findElements(By.cssSelector(".dataTable td:nth-child(5)"));
        //создаем список с зонами
        List<WebElement> zoneCountry = driver.findElements(By.cssSelector(".dataTable td:nth-child(6)"));
        //создаем список с названиями стран
        List<String> nameCountry = new ArrayList<>();
        for (WebElement i : country) {
            nameCountry.add(i.getText());
        }
        //Создаем список для конвертации текстового значения количества зон в числовое
        List<Integer> countZone = new ArrayList<>();
        for (WebElement i : zoneCountry) {
            countZone.add(Integer.valueOf(i.getText()));
        }
        //сравниваем количество зон с 0
        for (int i : countZone) {
            if (i > 0) {
                //получаем индекс элемета
                int k = countZone.indexOf(i);
                //находим на странице страну по названию и кликаем на неее
                driver.findElement(By.linkText(nameCountry.get(k))).click();
                //создаем список с зонами
                List<WebElement> zone = driver.findElements(By.cssSelector("#table-zones td:nth-child(3) input[name*=zones]"));
                //создаем два списка, в одном из которых произведем сортировку по алфавиту
                List<String> zoneName = new ArrayList<>();
                List<String> zoneNameSort = new ArrayList<>();
                //через цикл заполняем списки названиями зон
                for (WebElement n : zone) {
                    zoneName.add(n.getText());
                    zoneNameSort.add(n.getText());
                }
                //сортируем список по алфавиту
                Collections.sort(zoneNameSort);
                //сравниваем отсортированный список со списком который был получен со страницы
                assertEquals(zoneNameSort, zoneName);
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
            //создаем список из элементов с названиями зон
            List<WebElement> zone = driver.findElements(By.cssSelector("#table-zones td:nth-child(3) option[selected]"));
            //создаем два списка, в одном из которых произведем сортировку по алфавиту
            List<String> zoneName = new ArrayList<>();
            List<String> zoneNameSort = new ArrayList<>();
            //через цикл заполняем списки названиями зон
            for (WebElement n : zone) {
                zoneName.add(n.getText());
                zoneNameSort.add(n.getText());
            }
            //сортируем список по алфавиту
            Collections.sort(zoneNameSort);
            //сравниваем отсортированный список со списком который был получен со страницы
            assertEquals(zoneNameSort, zoneName);
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