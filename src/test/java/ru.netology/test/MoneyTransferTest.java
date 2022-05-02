package ru.netology.test;

import com.codeborne.selenide.Configuration;
import lombok.val;
import org.junit.jupiter.api.Test;
import ru.netology.data.DataHelper;
import ru.netology.page.LoginPage;

import static com.codeborne.selenide.Selenide.open;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class MoneyTransferTest {

    @Test
    void shouldTransferMoneyBetweenOwnCards1() {
        open("http://localhost:9999");
        Configuration.holdBrowserOpen = true;
        val loginPage = new LoginPage();
//    var loginPage = open("http://localhost:9999", LoginPageV2.class);
        val authInfo = DataHelper.getAuthInfo();
        val verificationPage = loginPage.validLogin(authInfo);
        val verificationCode = DataHelper.getVerificationCodeFor(authInfo);
        val dashboardPage = verificationPage.validVerify(verificationCode);
        val balanceFirstCardBefore = dashboardPage.getFirstCardBalance();
        val balanceSecondCardBefore = dashboardPage.getSecondCardBalance();
        val transferPage = dashboardPage.transferFromSecondToFirstCard();
        int amount = 1000;
        transferPage.transferMoney(amount, DataHelper.getSecondCardNumber());
        val balanceFirstCardAfter = dashboardPage.getFirstCardBalance();
        val balanceSecondCardAfter = dashboardPage.getSecondCardBalance();
        assertEquals((balanceSecondCardBefore - amount), balanceSecondCardAfter);
        assertEquals((balanceFirstCardBefore + amount), balanceFirstCardAfter);

    }

    @Test
    void shouldTransferMoneyBetweenOwnCards() {
        open("http://localhost:9999");
        Configuration.holdBrowserOpen = true;
        val loginPage = new LoginPage();
//    var loginPage = open("http://localhost:9999", LoginPageV2.class);
        val authInfo = DataHelper.getAuthInfo();
        val verificationPage = loginPage.validLogin(authInfo);
        val verificationCode = DataHelper.getVerificationCodeFor(authInfo);
        val dashboardPage = verificationPage.validVerify(verificationCode);
        val balanceFirstCardBefore = dashboardPage.getFirstCardBalance();
        val balanceSecondCardBefore = dashboardPage.getSecondCardBalance();
        val transferPage = dashboardPage.transferFromFirstToSecondCard();
        int amount = 0;
        transferPage.transferMoney(amount, DataHelper.getFirstCardNumber());
        val balanceFirstCardAfter = dashboardPage.getFirstCardBalance();
        val balanceSecondCardAfter = dashboardPage.getSecondCardBalance();
        assertEquals((balanceFirstCardBefore - amount), balanceFirstCardAfter);
        assertEquals((balanceSecondCardBefore + amount), balanceSecondCardAfter);

    }

    @Test
    void shouldTransferMoneyBetweenOwnCardsOverLimit() {
        open("http://localhost:9999");
        Configuration.holdBrowserOpen = true;
        val loginPage = new LoginPage();
//    var loginPage = open("http://localhost:9999", LoginPageV2.class);
        val authInfo = DataHelper.getAuthInfo();
        val verificationPage = loginPage.validLogin(authInfo);
        val verificationCode = DataHelper.getVerificationCodeFor(authInfo);
        val dashboardPage = verificationPage.validVerify(verificationCode);
        val balanceFirstCardBefore = dashboardPage.getFirstCardBalance();
        val balanceSecondCardBefore = dashboardPage.getSecondCardBalance();
        val transferPage = dashboardPage.transferFromFirstToSecondCard();
        int amount = 20000;
        transferPage.transferMoney(amount, DataHelper.getFirstCardNumber());
        transferPage.failedTransfer();

    }

}
