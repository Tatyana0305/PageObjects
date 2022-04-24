package ru.netology.page;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;
import ru.netology.data.DataHelper;

import static com.codeborne.selenide.Selectors.withText;
import static com.codeborne.selenide.Selenide.$;


public class MoneyTransferPage {
    private SelenideElement amount = $("[data-test-id=amount] input");
    private SelenideElement from = $("[data-test-id=from] input");
    private SelenideElement actionButton = $("[data-test-id=action-transfer]");


    public DashboardPage transferMoney(int amountTransfer, DataHelper.CardInfo cardInfo) {
        amount.setValue(String.valueOf(amountTransfer));
        from.setValue(cardInfo.getCardNumber());
        actionButton.click();
        return new DashboardPage();
    }


    public void successTransfer() {
        $(withText("Ваши карты")).shouldBe(Condition.visible);
    }

    public void failedTransfer() {
        $(withText("Недостаточно средств для перевода!")).shouldBe(Condition.visible);
    }

}