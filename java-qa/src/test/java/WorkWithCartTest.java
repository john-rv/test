import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;

public class WorkWithCartTest {
    public WebDriver driver;
    public WebDriverWait wait;

    @Before
    public void start(){
        driver = new ChromeDriver();
        wait = new WebDriverWait(driver, 5);
        driver.get("http://localhost/litecart/en/");
    }

    @Test
    public void workWithCart() {
        //проверяем количество в корзине
        int count = Integer.parseInt(driver.findElement(By.cssSelector("#cart span.quantity")).getText());
        //пока количество меньше 3 добавлем товар
           while (count < 3){
               //находим 1 товар из списка кликаем на него
               driver.findElement(By.cssSelector("#box-most-popular li:nth-child(1)")).click();
               //если на странице есть селект Size выбираем значение
               if (driver.findElements(By.className("options")).size() > 0){
                   Select select = new Select(driver.findElement(By.name("options[Size]")));
                   select.selectByIndex(1);
               }
               //нажимаем добавить
               driver.findElement(By.name("add_cart_product")).click();
               //увеличиваем счетчик на 1
               count++;
               //ждем пока счетчик корзины обновится, сравниваем значение со значением счетчика
               wait.until(ExpectedConditions.textToBe(By.cssSelector("#cart span.quantity"),String.valueOf(count)));
              //возвращаемся на главную
               driver.findElement(By.className("general-0")).click();
           }

        //переходим в корзину
        driver.findElement(By.linkText("Checkout »")).click();
        //получаем список элементов внизу таблицы
        List<WebElement> order = driver.findElements(By.cssSelector("td:nth-child(2).item"));
        //получаем размер списка
        int size = order.size();
            //пока количество в списке > 0
            while (size > 0){
            //нажимаем удалить
            driver.findElement(By.name("remove_cart_item")).click();
            //ждем когда исчезнет запись в списке
            wait.until(ExpectedConditions.stalenessOf(order.get(0)));
            //уменьшаем количество на 1
            size--;
        }
    }

        @After
    public void stop(){
        driver.quit();
        driver = null;
    }
}
