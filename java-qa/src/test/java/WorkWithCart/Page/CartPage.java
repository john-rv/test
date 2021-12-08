package WorkWithCart.Page;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.List;

import static org.openqa.selenium.support.ui.ExpectedConditions.numberOfElementsToBeLessThan;

public class CartPage extends Page {

    public CartPage(WebDriver driver) {
        super(driver);
    }
    //удаление всех товаров
    public void removeAll() {
        driver.findElement(By.linkText("Checkout »")).click();
        //получаем список элементов внизу таблицы
        List<WebElement> order = driver.findElements(By.cssSelector("td:nth-child(2).item"));
        //получаем размер списка
        int size = order.size();
        //пока количество в списке > 0
        while (size > 0) {
            //нажимаем удалить
            if (size != 1) {
                driver.findElement(By.cssSelector("li:nth-child(1).shortcut")).click();
            }
            driver.findElement(By.name("remove_cart_item")).click();
            //ждем когда количество записей будет меньше предыдущего размера списка
            wait.until(numberOfElementsToBeLessThan(By.cssSelector("td:nth-child(2).item"), size));
            //уменьшаем количество на 1
            size--;
        }
    }
    //удаление определенного количества товаров
    public void remove(int item) {
        driver.findElement(By.linkText("Checkout »")).click();
        //получаем список элементов внизу таблицы
        List<WebElement> order = driver.findElements(By.cssSelector("td:nth-child(2).item"));
        //получаем размер списка
        int size = order.size();
        if (size >= item) {
            //пока количество > 0
            while (item > 0) {
                //нажимаем удалить
                if (size != 1) {
                    driver.findElement(By.cssSelector("li:nth-child(1).shortcut")).click();
                }
                driver.findElement(By.name("remove_cart_item")).click();
                //ждем когда количество записей будет меньше предыдущего размера списка
                wait.until(numberOfElementsToBeLessThan(By.cssSelector("td:nth-child(2).item"), size));
                //уменьшаем количество на 1
                item--;
            }
        } else {
            System.out.println("В корзине количество товаром меньше чем вы хотите удалить");
        }
    }
}
