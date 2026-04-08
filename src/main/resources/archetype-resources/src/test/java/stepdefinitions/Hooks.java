#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.stepdefinitions;

import ${package}.utils.TestDataContainer;
import ${package}.utils.TestDataContainer.Keys;
import ${package}.webdriversession.WebDriverSession;
import ${package}.webdriversession.WebDriverSessionStore;
import ${package}.webdriversession.webdriverfactory.DriverType;
import io.cucumber.java.After;
import io.cucumber.java.AfterStep;
import io.cucumber.java.Before;
import io.cucumber.java.BeforeStep;
import io.cucumber.java.Scenario;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.assertj.core.api.SoftAssertions;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
//a11y-start
import org.assertj.core.api.SoftAssertions;
//a11y-end

import java.io.File;
import java.util.ArrayList;

public class Hooks {

    public Logger logger = LoggerFactory.getLogger(this.getClass());

    private final TestDataContainer testDataContainer;
    //a11y-start
    private final boolean skipA11Y = System.getProperty("skipA11y", "true").equalsIgnoreCase("true");
    //a11y-end

    public Hooks(TestDataContainer testDataContainer) {
        this.testDataContainer = testDataContainer;
    }

    @BeforeStep
    public void beforeStep() {
        testDataContainer.incrementStepIndex();
    }

    @Before
    public void beforeScenario(Scenario scenario) {
        int debuglength = 80;
        String fillchar = "${symbol_pound}";

        testDataContainer.setTestDataString(Keys.BASEURL, System.getProperty(Keys.BASEURL.getKeyValue(), "baseURL is not set, please add to your commandline '-DbaseURL=yourvalue or add to your runConfiguration"));
        testDataContainer.setScenario(scenario);
        testDataContainer.setTestDataBrowserType(DriverType.valueOf(System.getProperty("browser", "no browser set").toUpperCase()));
        testDataContainer.setTestDataString("browser.version", System.getProperty("browser.version", "no version set"));
        testDataContainer.setTestDataBoolean(Keys.INITIALIZED, false);
        testDataContainer.setTestDataInt(Keys.STEP_INDEX, 0);
        //a11y-start
        testDataContainer.setTestDataBoolean(Keys.SKIP_A11Y, skipA11Y);
        testDataContainer.setTestDataSoftAssertion("softAssertion.object", new SoftAssertions());
        testDataContainer.setTestDataBoolean(Keys.SOFTASSERTIONS_ACTIVE, !skipA11Y);

        if (!skipA11Y) {
            testDataContainer.setTestDataList("a11y.description", new ArrayList<>());
        }
        //a11y-end

        // to check if it runs on Jenkins or local
        String jobname = System.getenv("JOB_NAME");

        //Do Database resets here

        if (jobname != null) {
            testDataContainer.setTestDataBoolean(Keys.LOCALRUN, false);
            logger.info(
                    StringUtils.leftPad("JENKINS INFOS: ", debuglength, fillchar) + "\n" +
                            String.format("BUILD_NUMBER: %s", System.getProperty("BUILD_NUMBER")) + "\n" +
                            StringUtils.leftPad("${symbol_pound} BUILD_NUMBER:" + System.getenv("BUILD_NUMBER"), debuglength, fillchar) + "\n" +
                            StringUtils.leftPad("${symbol_pound} JOB_NAME: " + System.getenv("JOB_NAME"), debuglength, fillchar) + "\n" +
                            StringUtils.leftPad("${symbol_pound} JENKINS_URL: " + System.getenv("JENKINS_URL"), debuglength, fillchar) + "\n" +
                            StringUtils.leftPad("${symbol_pound} WORKSPACE: " + System.getenv("WORKSPACE"), debuglength, fillchar) + "\n" +
                            StringUtils.leftPad("${symbol_pound} NODE_NAME: " + System.getenv("NODE_NAME"), debuglength, fillchar) + "\n" +
                            StringUtils.leftPad(fillchar, debuglength, fillchar)
            );
        } else {
            testDataContainer.setTestDataBoolean(Keys.LOCALRUN, true);
        }

        logger.debug(StringUtils.leftPad(fillchar, debuglength, fillchar));
        logger.info("${symbol_pound} executing Scenario: " + scenario.getName());
        logger.debug(StringUtils.leftPad(fillchar, debuglength, fillchar));
    }

    @AfterStep
    public void afterStep(Scenario scenario) {
        //a11y-start
        testDataContainer.getAndClearA11YDescriptions().forEach(issue ->
                scenario.attach(issue.getBytes(), "text/plain", "A11Y Issue in step ${symbol_pound}" + testDataContainer.getStepIndex())
        );
        //a11y-end
    }

    @After(order = 1000)
    public void afterScenario(Scenario scenario) {
        logger.info("AfterScenario");

        String testId = testDataContainer.getTestId();
        WebDriverSession webDriverSession = WebDriverSessionStore.getIfExists(testDataContainer.getCurrentSessionId());

        if (!scenario.isFailed()) {
            WebDriverSessionStore.closeAll();
            return;
        }

        if (webDriverSession != null && webDriverSession.getCurrentPage() != null) {
            try {
                TakesScreenshot takesScreenshot = (TakesScreenshot) webDriverSession.getWebDriver();
                scenario.attach(takesScreenshot.getScreenshotAs(OutputType.BYTES), "image/png", "Screenshot");
                scenario.attach(webDriverSession.getWebDriver().getPageSource().getBytes(), "text/html", "Page Sourcecode");

                testDataContainer.getScreenshots().forEach(screenshot ->
                        scenario.attach(screenshot.getLeft(), "image/png", "Forced Screenshot " + screenshot.getRight())
                );

                WebDriver webDriver = webDriverSession.getWebDriver();
                if (testDataContainer.isLocalRun()) {
                    File screenshot = ((TakesScreenshot) webDriver).getScreenshotAs(OutputType.FILE);
                    FileUtils.copyFile(screenshot, new File(System.getProperty("user.dir") + "/target/error_selenium_" + testId + "_" + testDataContainer.getCurrentSessionId() + ".png"));
                } else {
                    byte[] screenshot = ((TakesScreenshot) webDriver).getScreenshotAs(OutputType.BYTES);
                    scenario.attach(screenshot, "image/png", "Screenshot");
                }
            } catch (Exception e) {
                logger.error("Error during afterScenario screenshot handling", e);
            } finally {
                WebDriverSessionStore.closeAll();
            }
        }
    }

    //a11y-start
    @After(order = 1100)
    public void softAssertAll() {
        if (testDataContainer.hasSoftAssertions() || !skipA11Y) {
            testDataContainer.getSoftAssertionObject().assertAll();
        }
    }
    //a11y-end
}
