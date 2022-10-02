package ru.netology.delivery.test;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.SelenideElement;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.Keys;
import ru.netology.delivery.data.DataGenerator;

import java.io.IOException;
import java.time.Duration;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selenide.$x;
import static com.codeborne.selenide.Selenide.open;

class DeliveryTest {

    @BeforeEach
    void setup() {
        open("http://localhost:9999");
    }

    @Test
    @DisplayName("Should successful plan and replan meeting")
    void shouldSuccessfulPlanAndReplanMeeting() throws IOException {

        Configuration.holdBrowserOpen = true;
        var validUser = DataGenerator.Registration.generateUser("ru");
        var daysToAddForFirstMeeting = 4;
        var firstMeetingDate = DataGenerator.generateDate(daysToAddForFirstMeeting);
        var daysToAddForSecondMeeting = 7;
        var secondMeetingDate = DataGenerator.generateDate(daysToAddForSecondMeeting);

        $x("//input[@placeholder=\"Город\"]").setValue(validUser.getCity());
        //$x("//input[@placeholder=\"Город\"]").setValue("Ростов-на-Дону");
        $x("//input[@placeholder=\"Город\"]").sendKeys(Keys.TAB);
        $x("//input[@placeholder=\"Дата встречи\"]").doubleClick();
        $x("//input[@placeholder=\"Дата встречи\"]").sendKeys(Keys.BACK_SPACE);
        $x("//input[@placeholder=\"Дата встречи\"]").setValue(firstMeetingDate);
        $x("//input[@placeholder=\"Дата встречи\"]").sendKeys(Keys.TAB);
        $x("//*[@name='name']").setValue(validUser.getName());
        $x("//*[@name='phone']").setValue(validUser.getPhone());
        $x("//*[@class=\"checkbox__box\"]").click();
        $x("//div[contains(@class, 'form-field')]/button[@role='button']").click();

        $x("//div[contains(@class,'notification notification_visible')]").shouldHave(Condition.text(firstMeetingDate), Duration.ofSeconds(15)).shouldBe(Condition.visible);
        $x("//div[contains(@class,'notification notification_visible')]").should(disappear, Duration.ofSeconds(15));

        $x("//input[@placeholder=\"Дата встречи\"]").doubleClick();
        $x("//input[@placeholder=\"Дата встречи\"]").sendKeys(Keys.BACK_SPACE);
        $x("//input[@placeholder=\"Дата встречи\"]").setValue(secondMeetingDate);
        $x("//input[@placeholder=\"Дата встречи\"]").sendKeys(Keys.TAB);
        $x("//div[contains(@class, 'form-field')]/button[@role='button']").click();
        $x("//div[contains(@class,'notification notification_visible')]").shouldHave(Condition.text("У вас уже запланирована встреча на другую дату. Перепланировать?"), Duration.ofSeconds(15)).shouldBe(Condition.visible);
        $x("//div[@class=\"notification__content\"]/button[@role=\"button\"]").click();

        $x("//div[contains(@class,'notification notification_visible')]").shouldHave(Condition.text(secondMeetingDate), Duration.ofSeconds(15)).shouldBe(Condition.visible);
    }

}
