import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.support.Color;

import java.io.IOException;
import java.util.Properties;

import static org.junit.Assert.assertTrue;

public class ProductPageTest {
    public WebDriver driver;
    public static Properties props;
    String browser;

    //проверяем что цена серая
    public boolean priceGray(WebElement element) {
        Color color = Color.fromString(String.valueOf(element.getCssValue("color")));
        int r = color.getColor().getRed();
        int b = color.getColor().getBlue();
        int g = color.getColor().getGreen();
        return (r == g && r == b);

    }

    //проверяем что цена зачеркнута
    public boolean priceCross(WebElement element) {
        if ("ie".equals(browser)) {
            return ("s".equals(element.getTagName()));
        } else {
            return ("line-through".equals(element.getCssValue("text-decoration-line")));
        }

    }

    //проверяем что цена жирная
    public boolean priceStrong(WebElement element) {
        return ("STRONG".equals(element.getAttribute("tagName")));
    }

    //проверяем что цена красная
    public boolean priceRed(WebElement element) {
        Color color = Color.fromString(String.valueOf(element.getCssValue("color")));
        int b = color.getColor().getBlue();
        int g = color.getColor().getGreen();
        return  (g == 0 && b == 0);
    }

    //проверяем что акционная цена крупнее обычной
    public boolean promoPriceBig(WebElement promo, WebElement regular) {
        String promoPrice = promo.getCssValue("font-size");
        String regularPrice = regular.getCssValue("font-size");
        double sizePP = Double.valueOf(promoPrice.replace("px", ""));
        double sizeRP = Double.valueOf(regularPrice.replace("px", ""));
        return (sizePP > sizeRP);
    }

    @Before
    public void init()  throws IOException {
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
        driver.get("http://localhost/litecart/en/");

    }

    @Test
    public void productPage() {
        //получаем название товара на главной странице
        String nameProductMain = driver.findElement(By.cssSelector("#box-campaigns .name")).getText();

        //получаем локаторы обычной и акционной цены на главной странице
        WebElement promoPriceMain = driver.findElement(By.cssSelector("#box-campaigns .campaign-price"));
        WebElement regularPrice = driver.findElement(By.cssSelector("#box-campaigns .regular-price"));

        //получаем значение акционной и обычной цены на главной странице
        String valuePromoPriceMain = promoPriceMain.getText();
        String valueRegularPriceMain = regularPrice.getText();

        //проверки на главной странице
        //проверяем что обычная цена зачеркнута
        assertTrue("Обычная цена на главной странице не зачеркнута", priceCross(regularPrice));
        //проверяем что цвет обычной цены серый
        assertTrue("Обычная на главной странице цена не серая", priceGray(regularPrice));
        //проверяем что акционная цена жирная
        assertTrue("Акционная на главной странице цена не жирная", priceStrong(promoPriceMain));
        //проверяем что акционная цена красная
        assertTrue("Акционная на главной странице цена не красная", priceRed(promoPriceMain));
        //проверяем что акционная цена крупнее чем обычная
        assertTrue("Размер акционной цены на главной странице меньше обычной цены", promoPriceBig(promoPriceMain, regularPrice));

        //переходим на карточку товара
        driver.findElement(By.cssSelector("#box-campaigns .name")).click();
        //получаем название товара на карточке товара
        String nameProductCard = driver.findElement(By.cssSelector("#box-product h1[itemprop = name]")).getText();
        //сравниваем название товара на главной странице и в карточке товара
        Assert.assertEquals("Название на главной странице и в карточке товара не совпадают", nameProductMain, nameProductCard);

        //получаем локаторы обычной и акционной цены на карточке товара
        WebElement regularPriceCard = driver.findElement(By.cssSelector("#box-product .regular-price"));
        WebElement promoPriceCard = driver.findElement((By.cssSelector("#box-product .campaign-price")));

        //получаем значение акционной и обычной цены на карточке товара
        String valueRegularPriceCard = regularPriceCard.getText();
        String valuePromoPriceCard = promoPriceCard.getText();
        //сравниваем акционную цену на главной странице и карточке товара
        Assert.assertEquals("Акционная цена на главной странице и карточке товара отличается", valuePromoPriceMain, valuePromoPriceCard);
        //сравниваем значение обычной цены на главной странице и карточке товара
        Assert.assertEquals("Обычная цена на главной странице и карточке товара отличается", valueRegularPriceMain, valueRegularPriceCard);

        //проверки на карточке товара
        //проверяем что обычная цена зачеркнута
        assertTrue("Обычная цена на карточке товара не зачеркнута", priceCross(regularPriceCard));
        //проверяем что цвет обычной цены серый
        assertTrue("Обычная цена на карточке товара не серая", priceGray(regularPriceCard));
        //проверяем что акционная цена жирная
        assertTrue("Акционная цена на карточке товара не жирная", priceStrong(promoPriceCard));
        //проверяем что акционная цена красная
        assertTrue("Акционная цена на карточке не красная", priceRed(promoPriceCard));
        //проверяем что акционная цена крупнее чем обычная
        assertTrue("Размер акционной цены меньше обычной цены", promoPriceBig(promoPriceCard, regularPriceCard));

    }

    @After
    public void stop() {
        driver.quit();
        driver = null;
    }
}
