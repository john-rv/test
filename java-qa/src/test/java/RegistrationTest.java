import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.Select;

import java.util.Random;
import java.util.concurrent.TimeUnit;

public class RegistrationTest {
    public WebDriver driver;

    Random random = new Random();
    int i = random.nextInt(1000);
    String email = "pochta" + i + "@mail.ru";
    String pass = random.toString();
    String postCode = String.valueOf(code(10000,99999));

    //генерация кода в заданом диапазоне
    public int code(int min, int max){
        return (random.nextInt(max - min) + min );
    }

    @Before
    public void start(){
        driver = new ChromeDriver();
        driver.manage().timeouts().implicitlyWait(3, TimeUnit.SECONDS);
    }

    @Test
    public void registration(){
        driver.get("http://localhost/litecart/en/");
        //переходим на форму регистрации
        driver.findElement(By.cssSelector("[name = login_form] tr:nth-child(5)")).click();
        //находим элемент форма
        WebElement form = driver.findElement(By.cssSelector("[name=customer_form]"));
        //находим поля на форме и заполяем их
        //Tax ID
        form.findElement(By.cssSelector("[name = tax_id]")).sendKeys("1");
        //Company
        form.findElement(By.cssSelector("[name = company]")).sendKeys("stqa");
        //First Name
        form.findElement(By.cssSelector("[name = firstname]")).sendKeys("Ivan");
        //Last Name
        form.findElement(By.cssSelector("[name = lastname]")).sendKeys("Petrov");
        //Address 1
        form.findElement(By.cssSelector("[name = address1]")).sendKeys("str.Arbat 33");
        //Address 2
        form.findElement(By.cssSelector("[name = address2]")).sendKeys("str.Lenina 33");
        //Postcode
        form.findElement(By.cssSelector("[name = postcode]")).sendKeys(postCode);
        //City
        form.findElement(By.cssSelector("[name = city]")).sendKeys("Moscow");
        //Country
        Select country = new Select(driver.findElement(By.cssSelector("select[name = country_code]")));
        country.selectByVisibleText("United States");
        //Zone/State/Province
        Select state = new Select(driver.findElement(By.cssSelector("select[name = zone_code]")));
        state.selectByIndex(code(1,state.getOptions().size()));
        //Email
        form.findElement(By.cssSelector("[name = email]")).sendKeys(email);
        //Phone
        form.findElement(By.cssSelector("[name = phone]")).sendKeys("12313312");
        //Desired Password
        form.findElement(By.cssSelector("[name = password]")).sendKeys(pass);
        //Confirm Password
        form.findElement(By.cssSelector("[name = confirmed_password]")).sendKeys(pass);
        //Create account
        form.findElement(By.cssSelector("[name = create_account]")).click();
        //Logout
        driver.findElement(By.linkText("Logout")).click();

        //авторизуемся на главной странице
        //Email
        driver.findElement(By.cssSelector("input[name = email]")).sendKeys(email);
        //Password
        driver.findElement(By.cssSelector("input[name = password]")).sendKeys(pass);
        //Login
        driver.findElement(By.cssSelector("button[name = login]")).click();
        //Logout
        driver.findElement(By.linkText("Logout")).click();
    }

    @After
    public void stop(){
        driver.quit();
        driver = null;
    }
}
