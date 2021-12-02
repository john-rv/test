import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.File;
import java.io.IOException;
import java.util.Properties;
import java.util.Random;

public class AddProductTest {
    public WebDriver driver;
    public WebDriverWait wait;
    public static Properties props;
    String browser;

    Random random = new Random();
    int i = random.nextInt(1000);
    String code = String.valueOf(i);
    String product = "duck" + i;

    //загрузка файла
    public void unhide(WebDriver driver, WebElement element) {
        String script = "arguments[0].style.opacity=1;"
                + "arguments[0].style['transform']='translate(0px, 0px) scale(1)';"
                + "arguments[0].style['MozTransform']='translate(0px, 0px) scale(1)';"
                + "arguments[0].style['WebkitTransform']='translate(0px, 0px) scale(1)';"
                + "arguments[0].style['msTransform']='translate(0px, 0px) scale(1)';"
                + "arguments[0].style['OTransform']='translate(0px, 0px) scale(1)';"
                + "return true;";
        ((JavascriptExecutor) driver).executeScript(script, element);
    }

    public void attachFile(WebDriver driver, By locator, String file) {
        WebElement input = driver.findElement(locator);
        unhide(driver, input);
        input.sendKeys(file);
    }

    //заполнение даты
    public void browserDate(By locator) {
        if ("firefox".equals(browser)) {
            driver.findElement(locator).sendKeys("2021-11-29");
        } else {
            driver.findElement(locator).sendKeys("29112021");
        }
    }

    @Before
    public void start() throws IOException {
        props = new Properties();
        props.load(ProductPageTest.class.getResourceAsStream("test.properties"));
        browser = props.getProperty("browser");

        if ("firefox".equals(browser)) {
            driver = new FirefoxDriver();
        } else if ("chrome".equals(browser)) {
            driver = new ChromeDriver();
        } else if ("ie".equals(browser)) {
            driver = new InternetExplorerDriver();
        }

        wait = new WebDriverWait(driver, 10);
        driver.manage().window().maximize();

        driver.get("http://localhost/litecart/admin/login.php");
        driver.findElement(By.name("username")).sendKeys("admin");
        driver.findElement(By.name("password")).sendKeys("admin");
        driver.findElement(By.name("login")).click();
    }

    @Test
    public void addProduct() {
        //переходим на форму добавления товара
        driver.findElement(By.cssSelector("#box-apps-menu li:nth-child(2)")).click();
        driver.findElement(By.cssSelector("a:nth-child(2).button")).click();

        //заполняем поля на вкладке General
        //status
        driver.findElement(By.cssSelector("label:nth-child(3) > input[type=radio]")).click();
        //name
        driver.findElement(By.name("name[en]")).sendKeys(product);
        //code
        driver.findElement(By.name("code")).sendKeys(code);
        //Categories Rubber Ducks
        driver.findElement(By.cssSelector("tr:nth-child(4) input[data-name=Root]")).click();
        driver.findElement(By.cssSelector("tr:nth-child(4) input[data-name=\"Rubber Ducks\"]")).click();
        //Product Groups Female
        driver.findElement(By.cssSelector("tr:nth-child(7) input[name=\"product_groups[]\"]")).click();
        //Quantity
        driver.findElement(By.name("quantity")).sendKeys(code);
        //Upload Images
        File image = new File("src/test/img/duck.jpg");
        attachFile(driver, By.name("new_images[]"), image.getAbsolutePath());
        //Date Valid From
        browserDate(By.name("date_valid_from"));
        //Date Valid To
        browserDate(By.name("date_valid_to"));

        //переключаемся на Information и заполняем поля
        driver.findElement(By.cssSelector("form ul li:nth-child(2)")).click();
        wait.until(ExpectedConditions.attributeToBe(By.cssSelector("form ul li:nth-child(2)"), "className", "active"));
        //Manufacturer
        Select manufacturer = new Select(driver.findElement(By.name("manufacturer_id")));
        manufacturer.selectByValue("1");
        //Keywords
        driver.findElement(By.name("keywords")).sendKeys(product);
        //Short Description
        driver.findElement(By.name("short_description[en]")).sendKeys(product);
        //Description
        driver.findElement(By.cssSelector("tr:nth-child(5) .trumbowyg-editor")).sendKeys(product);
        //Head Title
        driver.findElement(By.name("head_title[en]")).sendKeys(product);
        //Meta Description
        driver.findElement(By.name("meta_description[en]")).sendKeys(product);

        //переключаемся на Prices и заполняем поля
        driver.findElement(By.cssSelector("form ul li:nth-child(4)")).click();
        wait.until(ExpectedConditions.attributeToBe(By.cssSelector("form ul li:nth-child(4)"), "className", "active"));
        //Purchase Price
        driver.findElement(By.name("purchase_price")).sendKeys(code);
        //purchase_price_currency_code
        driver.findElement(By.name("purchase_price_currency_code")).click();
        Select currencyCode = new Select(driver.findElement(By.name("purchase_price_currency_code")));
        currencyCode.selectByVisibleText("US Dollars");
        //Price
        driver.findElement(By.name("prices[USD]")).sendKeys(code);
        driver.findElement(By.name("prices[EUR]")).sendKeys(code);

        //сохраняем карточку
        driver.findElement(By.name("save")).click();

        //переходим в папку Rubber Ducks
        driver.findElement(By.cssSelector(".dataTable tr:nth-child(3)  td:nth-child(3) a "));
        //проверям наличие товара в каталоге
        Assert.assertTrue("Товар не добавлен в каталог", driver.findElement(By.linkText(product)).isDisplayed());
    }

    @After
    public void stop() {
        driver.quit();
        driver = null;
    }
}
