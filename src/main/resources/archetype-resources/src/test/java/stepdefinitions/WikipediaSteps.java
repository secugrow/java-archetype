#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.stepdefinitions;

import ${package}.pageobjects.WikipediaContentPage;
import ${package}.pageobjects.WikipediaStartPage;
import ${package}.utils.TestDataContainer;
import org.openqa.selenium.WebElement;

import static org.assertj.core.api.Assertions.assertThat;

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
            WebElement searchbar = getPage(WikipediaStartPage.class).getSearchbar();
            assertThat(searchbar.isEnabled()).isTrue();
        });

        When("the Selenium page is opened", () -> {
            getPage(WikipediaStartPage.class).searchFor("Selenium");
        });

        Then("the header should be {string}", (String expected_header) -> assertThat(expected_header).isEqualTo(getPage(WikipediaContentPage.class).getHeader()));

    }
}
