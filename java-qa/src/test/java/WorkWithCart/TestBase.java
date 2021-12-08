package WorkWithCart;

import WorkWithCart.Page.CartPage;
import WorkWithCart.Page.MainPage;
import WorkWithCart.Page.ProductPage;
import org.junit.After;
import org.junit.Before;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

public class TestBase {
    public WebDriver driver;
    public WebDriverWait wait;

    MainPage mainPage;
    ProductPage productPage;
    CartPage cartPage;

    @Before
    public void start() {
        driver = new ChromeDriver();
        wait = new WebDriverWait(driver, 5);

        mainPage = new MainPage(driver);
        productPage = new ProductPage(driver);
        cartPage = new CartPage(driver);
    }

    @After
    public void stop() {
        driver.quit();
        driver = null;
    }
}
