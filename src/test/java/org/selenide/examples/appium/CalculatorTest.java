package org.selenide.examples.appium;

import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.WebDriverRunner;
import com.codeborne.selenide.appium.AppiumDriverRunner;
import io.appium.java_client.android.AndroidDriver;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.ScreenOrientation;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.events.WebDriverListener;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Selenide.*;
import static com.codeborne.selenide.appium.AppiumSelectors.byAttribute;

/**
 * Thanks to <a href="https://github.com/mkpythonanywhereblog">Maryna Kolesnik</a> for this example!
 * Her original sample can be found <a href="https://gist.github.com/mkpythonanywhereblog/d1fb3dca2e66146f519f">here</a>
 */
public class CalculatorTest {

  @BeforeEach
  void setUp() {
    closeWebDriver();
    Configuration.browserSize = null;
    Configuration.browser = AndroidDriverWithCalculator.class.getName();
    WebDriverRunner.addListener(new WebDriverListener() {
      @Override
      public void beforeClick(WebElement element) {
        WebDriverListener.super.beforeClick(element);
      }
    });

    open();
  }

  @Test
  void calculator() {
    $(By.id("digit_2")).click();
    $(By.id("op_add")).click();
    $(By.id("digit_4")).click();
    $(By.id("eq")).click();
    $(By.id("result")).shouldHave(text("6"));
  }
  @Test
  void calculatorPercents() {
    AndroidDriver androidDriver = AppiumDriverRunner.getAndroidDriver();
    androidDriver.rotate(ScreenOrientation.LANDSCAPE);
    $(By.id("digit_5")).click();
    $(By.id("digit_0")).click();
    $(By.id("digit_0")).click();
    $(By.id("op_mul")).click();
    $(By.id("digit_1")).click();
    $(By.id("digit_0")).click();
    $(byAttribute("content-desc", "percent")).click();
    $(By.id("eq")).click();
    $(By.id("result")).shouldHave(text("50"));
    androidDriver.rotate(ScreenOrientation.PORTRAIT);
  }
  @Test
  void calculatorSquareRoot() {
    $(byAttribute("content-desc", "square root")).click();
    $(By.id("lparen")).click();
    $(By.id("digit_1")).click();
    $(By.id("digit_0")).click();
    $(By.id("digit_0")).click();
    $(By.id("op_add")).click();
    $(By.id("digit_3")).click();
    $(By.id("digit_0")).click();
    $(By.id("digit_0")).click();
    $(By.id("rparen")).click();
    $(By.id("eq")).click();
    $(By.id("result")).shouldHave(text("20"));
  }
}
