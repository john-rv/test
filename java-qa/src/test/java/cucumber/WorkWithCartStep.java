package cucumber;

import WorkWithCart.TestBase;
import io.cucumber.java8.Ru;

public class WorkWithCartStep extends TestBase implements Ru {

    public WorkWithCartStep() {
        Дано("открыта главная страница магазина$", () -> {
            start();
            mainPage.getUrl();
        });
        Когда("в корзину добавлено (\\d+) товара$", (Integer arg0) -> {
            int count = arg0;
            while (count > 0) {
                mainPage.selectProduct();
                productPage.selectSize().addProduct();
                count--;
            }
        });
        Тогда("перейти в корзину и удалить все товары$", () -> {
            cartPage.removeAll();
            stop();
        });
    }
}

