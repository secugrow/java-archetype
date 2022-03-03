#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.stepdefinitions;

import ${package}.pageobjects.AbstractPage;
import ${package}.pageobjects.MainPage;
import ${package}.pageobjects.PageNotFoundException;
import ${package}.utils.TestDataContainer;
import ${package}.webdriversession.WebDriverSession;
import ${package}.webdriversession.WebDriverSessionStore;
import io.cucumber.java8.En;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Optional;

public abstract class AbstractStepDefs implements En {

    public Logger logger = LoggerFactory.getLogger(this.getClass());

    private TestDataContainer testDataContainer;

    public AbstractStepDefs(TestDataContainer tdc) {
        logger.info("Constructor AbstractStepDefs");
        testDataContainer = tdc;

        Before(scenario -> {
            logger.info("Before All Once");
            testDataContainer.setTestDataString(TestDataContainer.Keys.BASEURL, System.getProperty(TestDataContainer.Keys.BASEURL.getKeyValue(), "baseUrl is not set, please add to your commandline '-DbaseUrl=yourvalue or add to your runConfigurtaion"));
            String scenarioID = scenario.getName();
            testDataContainer.setTestDataString(TestDataContainer.Keys.TEST_ID, scenarioID);
        });

        AfterStep(scenario -> {
            if (scenario.isFailed()) {
                logger.info("AfterStep");
            }
        });

        After(scenario -> {
            logger.info("AfterAll Once");

            if (scenario.isFailed()) {
                TakesScreenshot takesScreenshot = ((TakesScreenshot) getCurrentWebDriver());
                scenario.attach(takesScreenshot.getScreenshotAs(OutputType.BYTES), "image/png", "Screenshot");
                scenario.attach(getCurrentWebDriver().getPageSource().getBytes(), "text/html", "Page Sourcecode");
            }
            logger.info("Closing all open Sessions! That could be fatal for parallel Execution");
            WebDriverSessionStore.closeAll();

        });
    }

    public WebDriver getCurrentWebDriver() {
        return getCurrentWebDriverSession().getWebDriver();
    }

    public WebDriverSession getCurrentWebDriverSession() {
        //TODO Add here thread name for junit 5 parallel execution
        String testID = testDataContainer.getTestId();
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

}
