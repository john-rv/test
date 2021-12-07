import com.browserup.bup.BrowserUpProxy;
import com.browserup.bup.BrowserUpProxyServer;
import com.browserup.bup.client.ClientUtil;
import com.browserup.harreader.model.Har;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.Proxy;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.net.MalformedURLException;

import static org.openqa.selenium.support.ui.ExpectedConditions.elementToBeClickable;
import static org.openqa.selenium.support.ui.ExpectedConditions.titleIs;

public class ProxyTest {
    private WebDriver driver;
    private WebDriverWait wait;
    public BrowserUpProxy proxy;


    @Before
    public void start() {
        // start the proxy
        proxy = new BrowserUpProxyServer();
        proxy.start();

        // get the Selenium proxy object
        Proxy seleniumProxy = ClientUtil.createSeleniumProxy(proxy);
        // configure it as a desired capability
        DesiredCapabilities capabilities = new DesiredCapabilities();
        capabilities.setCapability(CapabilityType.PROXY, seleniumProxy);
        FirefoxOptions options = new FirefoxOptions();
        options.merge(capabilities);
        driver = new FirefoxDriver(options);

    }

    @Test
    public void login() {
        proxy.newHar();
        driver.get("http://ya.ru/");
        driver.findElement(By.cssSelector("input.input__control")).sendKeys("webdriver", Keys.ENTER);
        Har har = proxy.endHar();
        har.getLog().getEntries().forEach(l -> System.out.println(l.getResponse().getStatus() + ":" + l.getRequest().getUrl()));
    }
    @After
    public void stop(){
        driver.quit();
        driver = null;
    }
}
