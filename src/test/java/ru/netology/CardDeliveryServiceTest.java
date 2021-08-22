package ru.netology;

import com.codeborne.selenide.Selenide;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.Keys;

import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selectors.withText;
import static com.codeborne.selenide.Selenide.*;

public class CardDeliveryServiceTest {

    private String deliveryDate(int daysToAdd) {
        LocalDate date = LocalDate.now();
        LocalDate deliveryDate = date.plusDays(daysToAdd);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
        String deliveryDateText = deliveryDate.format(formatter);
        return deliveryDateText;
    }

    @Test
    void shouldSubmitRequestWithFullData() {

        open("http://localhost:9999");
        $$("input[type=\"text\"]").first().setValue("Владимир");
        $("[data-test-id='date'] [pattern]").sendKeys(Keys.CONTROL + "A", Keys.DELETE);
        $("[data-test-id=\"date\"] [pattern]").setValue(deliveryDate(7));
        $("[name=\"name\"]").setValue("Анина Анна-Мария");
        $("[name=\"phone\"]").setValue("+79053330011");
        $("[data-test-id=\"agreement\"]").click();
        $(byText("Забронировать")).click();
        $("[data-test-id=\"notification\"]").shouldBe(visible, Duration.ofSeconds(15))
                .shouldHave(text("Встреча успешно забронирована на " + deliveryDate(7)));
    }
}
