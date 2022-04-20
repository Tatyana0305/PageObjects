package ru.netology.test;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.Configuration;
import org.junit.jupiter.api.Test;
import ru.netology.data.DataHelper;
import ru.netology.page.DashboardPage;
import ru.netology.page.LoginPage;
import ru.netology.page.MoneyTransferPage;

import static com.codeborne.selenide.Selectors.withText;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class MoneyTransferTest {
    @Test
    void shouldTransferMoneyBetweenOwnCards() {
        open("http://localhost:9999");
        Configuration.holdBrowserOpen = true;
        var loginPage = new LoginPage();
//    var loginPage = open("http://localhost:9999", LoginPageV2.class);
        var authInfo = DataHelper.getAuthInfo();
        var verificationPage = loginPage.validLogin(authInfo);
        var verificationCode = DataHelper.getVerificationCodeFor(authInfo);
        verificationPage.validVerify(verificationCode);
        new MoneyTransferPage().transferMoney("100");
        var actual = new DashboardPage().getCardBalance(0);
        int expected = 10100;
        assertEquals(expected, actual);
        new MoneyTransferPage().defaultMoney("100");
    }

    @Test
    void shouldTransferMoneyBetweenOwnCardsOverBalance() {
        open("http://localhost:9999");
        Configuration.holdBrowserOpen = true;
        var loginPage = new LoginPage();
//    var loginPage = open("http://localhost:9999", LoginPageV2.class);
        var authInfo = DataHelper.getAuthInfo();
        var verificationPage = loginPage.validLogin(authInfo);
        var verificationCode = DataHelper.getVerificationCodeFor(authInfo);
        verificationPage.validVerify(verificationCode);
        new MoneyTransferPage().transferMoney("20000");
        var actual = new DashboardPage().getCardBalance(0);
        int expected = 10000;
        assertEquals(expected, actual);
        new MoneyTransferPage().defaultMoney("20000");
        $(withText("Недостаточно средств для перевода!")).shouldBe(Condition.visible);

    }

}
