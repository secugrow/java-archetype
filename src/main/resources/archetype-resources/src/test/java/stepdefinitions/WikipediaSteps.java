#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.stepdefinitions;

import ${package}.pageobjects.WikipediaContentPage;
import ${package}.pageobjects.WikipediaStartPage;
import ${package}.utils.TestDataContainer;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class WikipediaSteps extends AbstractStepDefs {

    TestDataContainer testDataContainer;

    public WikipediaSteps(TestDataContainer testDc) {
        super(testDc);
        testDataContainer = testDc;

        Given("the start page is loaded", () -> {
            getCurrentWebDriver().navigate().to(testDataContainer.getBaseUrl());
            new WikipediaStartPage(getCurrentWebDriverSession());
        });

        Then("the searchbar is visible", () -> {
            WebElement searchbar = new WebDriverWait(getCurrentWebDriver(), Duration.ofSeconds(10)).until(ExpectedConditions.presenceOfElementLocated(By.id("txtSearch")));
            assertTrue(searchbar.isEnabled());
        });

        When("the Selenium page is opened", () -> {
            getPage(WikipediaStartPage.class).searchFor("Selenium");
        });

        Then("the header should be {string}", (String expected_header) -> assertEquals(expected_header, getPage(WikipediaContentPage.class).getHeader()));

    }
}
