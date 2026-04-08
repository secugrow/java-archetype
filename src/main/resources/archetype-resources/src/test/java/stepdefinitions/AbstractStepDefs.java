#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.stepdefinitions;

import com.deque.html.axecore.results.Rule;
import io.cucumber.java.Scenario;
import io.cucumber.java8.En;
import ${package}.pageobjects.AbstractPage;
import ${package}.pageobjects.MainPage;
import ${package}.pageobjects.PageNotFoundException;
import ${package}.utils.TestDataContainer;
import ${package}.webdriversession.WebDriverSession;
import ${package}.webdriversession.WebDriverSessionStore;
import org.assertj.core.api.SoftAssertions;
import org.assertj.core.description.TextDescription;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
//a11y-start
import ${package}.a11y.A11yHelper;
//a11y-end

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public abstract class AbstractStepDefs implements En {

    public Logger logger = LoggerFactory.getLogger(this.getClass());

    private TestDataContainer testDataContainer;

    public AbstractStepDefs(TestDataContainer tdc) {
        logger.info("Constructor AbstractStepDefs");
        testDataContainer = tdc;
    }

    public WebDriver getCurrentWebDriver() {
        return getCurrentWebDriverSession().getWebDriver();
    }

    public WebDriverSession getCurrentWebDriverSession() {
        //TODO Add here thread name for junit 5 parallel execution
        String testID = testDataContainer.getTestId();
        testDataContainer.setTestDataString("session.id", testID);
        return WebDriverSessionStore.getOrCreate(testID);
    }

    public WebDriverSession getOrCreateWebDriverSession(String key) {
        WebDriverSession session;
        session = WebDriverSessionStore.getOrCreate(key);
        return session;
    }

    public MainPage startWithMainPage(WebDriver webDriver) {
        webDriver.navigate().to(testDataContainer.getBaseUrl());
        return new MainPage(getCurrentWebDriverSession());
    }

    public <T extends AbstractPage> T getPage(Class<T> pageClass) {
        Optional<T> page = getCurrentWebDriverSession().getPage(pageClass);
        if (page.isPresent()) {
            //a11y-start
            doA11YCheck();
            //a11y-end
            return page.get();
        }
        throw new PageNotFoundException("page of type " + pageClass.getSimpleName() + " not open, but instead " + getCurrentPage());
    }

    protected AbstractPage getCurrentPage() {
        return getCurrentWebDriverSession().getCurrentPage();
    }

    protected String getTestIdForUser(String user) {
        return testDataContainer.getTestId().concat("_").concat(user);
    }


    //a11y-start
    private void doA11YCheck() {
        if (testDataContainer.doA11YCheck()) {
            Scenario scenario = testDataContainer.getScenario();
            List<String> a11yExclusions = extractA11YExclusions(scenario);
            WebDriver webDriver = getCurrentWebDriver();
            List<Rule> issues = A11yHelper.hasAccessibilityIssues(webDriver, a11yExclusions);

            if (!issues.isEmpty()) {
                issues.forEach(violation -> {
                    String violationString = "Violated Rule: " + violation.getId() + "on page " +
                            getCurrentPage().toString() +
                            violation.getNodes(); //fixme join nodes html

                    testDataContainer.addScreenshot(((TakesScreenshot) webDriver).getScreenshotAs(OutputType.BYTES), "Forced A11y screenshot for step TODO " + violation.getDescription() );
                    testDataContainer.addA11ydescription(violationString);
                });



                SoftAssertions softAssertions = testDataContainer.getSoftAssertionObject();
                softAssertions.assertThat(issues).as(new TextDescription("Found ${issues.size} relevant A11Y violations in sceanrio '${scenario.name}' step# ${testDataContainer.getStepIndex()}"))
                        .isEmpty();
            }
        }
    }


    private List<String> extractA11YExclusions(Scenario scenario) {
        return scenario.getSourceTagNames().stream().filter(tagNames -> tagNames.startsWith("@A11YExclude:")).map(exclusion -> {
            return exclusion.substring(exclusion.indexOf(":") + 1);
        }).collect(Collectors.toList());
    }
//a11y-end

}
