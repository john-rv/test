package WorkWithCart.Page;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;


public class ProductPage extends Page {

    public ProductPage(WebDriver driver) {
        super(driver);
    }

    public ProductPage selectSize() {
        if (driver.findElements(By.className("options")).size() > 0) {
            new Select(driver.findElement(By.name("options[Size]"))).selectByIndex(1);
        }
        return this;
    }

    public ProductPage addProduct() {
        int count = Integer.parseInt(driver.findElement(By.cssSelector("#cart span.quantity")).getText());
        driver.findElement(By.name("add_cart_product")).click();
        count++;
        wait.until(ExpectedConditions.textToBe(By.cssSelector("#cart span.quantity"), String.valueOf(count)));
        //возвращаемся на главную
        driver.findElement(By.className("general-0")).click();
        return this;
    }
}

