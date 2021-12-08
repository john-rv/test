package WorkWithCart.Page;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class MainPage extends Page {

    public MainPage(WebDriver driver) {
        super(driver);
    }

    public MainPage getUrl() {
        driver.get("http://localhost/litecart/en/");
        return this;
    }

    public MainPage selectProduct(){
        driver.findElement(By.cssSelector("#box-most-popular li:nth-child(1)")).click();
        return this;
    }

}

