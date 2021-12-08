package WorkWithCart;

import org.junit.Test;

public class WorkWithCartPoTest extends TestBase {

    @Test
    public void workWithCartPo() {

        mainPage.getUrl();
        int count = 3;
        while (count > 0) {
            mainPage.selectProduct();
            productPage.selectSize().addProduct();
            count--;
        }
        cartPage.removeAll();
    }
}
