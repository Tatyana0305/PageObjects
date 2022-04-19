package ru.netology.page;
import com.codeborne.selenide.Condition;
import org.openqa.selenium.Keys;

import static com.codeborne.selenide.Selectors.withText;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;


public class MoneyTransferPage {

    private String card1 = "5559 0000 0000 0001";
    private String card2 = "5559 0000 0000 0002";

    public DashboardPage transferMoney(String amount) {
        $$(withText("Пополнить")).first().click();
        $("[data-test-id='amount'] input").setValue(amount);
        $("[data-test-id='from'] input").setValue(card2);
        $("[data-test-id='action-transfer']").click();
        return new DashboardPage();
    }

    public DashboardPage defaultMoney(String amount) {
        $$(withText("Пополнить")).last().click();
        $("[data-test-id='amount'] input").sendKeys(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.BACK_SPACE);
        $("[data-test-id='amount'] input").setValue(amount);
        $("[data-test-id='from'] input").sendKeys(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.BACK_SPACE);
        $("[data-test-id='from'] input").setValue(card1);
        $("[data-test-id='action-transfer']").click();
        return new DashboardPage();
    }
    public void successTransfer() {
        $(withText("Ваши карты")).shouldBe(Condition.visible);
    }

    public void failedTransfer() {
        $(withText("Недостаточно средств для перевода!")).shouldBe(Condition.visible);
    }
}
