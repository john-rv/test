import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

public class LoginAdminTest {

    private WebDriver driver;

    @Before
    public void start(){
        driver = new ChromeDriver();
    }

    @Test
    public void login() {
        driver.get("http://localhost/litecart/admin/login.php");
        driver.findElement(By.name("username")).sendKeys("admin");
        driver.findElement(By.name("password")).sendKeys("admin");
        driver.findElement(By.name("login")).click();
    }
    @After
    public void stop(){
        driver.quit();
        driver = null;
    }
}
