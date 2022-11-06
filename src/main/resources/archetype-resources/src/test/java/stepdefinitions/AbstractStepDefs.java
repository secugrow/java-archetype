#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.stepdefinitions;

import com.deque.html.axecore.results.Rule;
import io.cucumber.java.Scenario;
import ${package}.pageobjects.AbstractPage;
import ${package}.pageobjects.MainPage;
import ${package}.pageobjects.PageNotFoundException;
import ${package}.utils.TestDataContainer;
import ${package}.utils.TestDataContainer.Keys;
import ${package}.webdriversession.WebDriverSession;
import ${package}.webdriversession.WebDriverSessionStore;
import ${package}.webdriversession.webdriverfactory.DriverType;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.io.FileUtils;
import io.cucumber.java8.En;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
//a11y-start
import ${package}.a11y.A11yHelper;
//a11y-end
import org.assertj.core.api.SoftAssertions;
import org.assertj.core.description.TextDescription;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;

import java.util.List;
import java.io.File;
import java.util.Collections;
import java.util.Optional;
import java.util.stream.Collectors;

public abstract class AbstractStepDefs implements En {

    public Logger logger = LoggerFactory.getLogger(this.getClass());

    private TestDataContainer testDataContainer;
    private boolean skipA11Y = System.getProperty("skipA11y", "true").equalsIgnoreCase("true");

    public AbstractStepDefs(TestDataContainer tdc) {
        logger.info("Constructor AbstractStepDefs");
        testDataContainer = tdc;
        int debuglength = 80;
        String fillchar = "#";


        Before(scenario -> {
            logger.info("Before All Once");
            testDataContainer.setTestDataString(Keys.BASEURL, System.getProperty(Keys.BASEURL.getKeyValue(), "baseUrl is not set, please add to your commandline '-DbaseUrl=yourvalue or add to your runConfigurtaion"));
            String scenarioID = scenario.getName();
            testDataContainer.setTestDataString(Keys.TEST_ID, scenarioID);
            testDataContainer.setTestDataBrowserType(DriverType.valueOf(System.getProperty("browser", "no browser set").toUpperCase()));
            testDataContainer.setTestDataString("browser.version", System.getProperty("browser.version", "no version set"));
            testDataContainer.setTestDataBoolean(Keys.INITIALIZED, false);
            testDataContainer.setTestDataInt(Keys.STEP_INDEX, 0);
            //a11y-start
            testDataContainer.setTestDataBoolean(Keys.SKIP_A11Y, skipA11Y);
            testDataContainer.setTestDataSoftAssertion("softAssertion.object", new SoftAssertions());
            testDataContainer.setTestDataBoolean(Keys.SOFTASSERTIONS_ACTIVE, !skipA11Y);

            if (!skipA11Y) {
                testDataContainer.setTestDataList("a11y.description", Collections.emptyList());
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
                                StringUtils.leftPad("# BUILD_NUMBER:" + System.getenv("BUILD_NUMBER"), debuglength, fillchar) + "\n" +
                                StringUtils.leftPad("# JOB_NAME: " + System.getenv("JOB_NAME"), debuglength, fillchar) + "\n" +
                                StringUtils.leftPad("# JENKINS_URL: " + System.getenv("JENKINS_URL"), debuglength, fillchar) + "\n" +
                                StringUtils.leftPad("# WORKSPACE: " + System.getenv("WORKSPACE"), debuglength, fillchar) + "\n" +
                                StringUtils.leftPad("# NODE_NAME: " + System.getenv("NODE_NAME"), debuglength, fillchar) + "\n" +
                                StringUtils.leftPad(fillchar, debuglength, fillchar)
                );

            } else {
                testDataContainer.setTestDataBoolean(Keys.LOCALRUN, true);
            }



            logger.debug(StringUtils.leftPad(fillchar, debuglength, fillchar));
            logger.debug(StringUtils.leftPad("# executing Scenario: " +  scenario.getName(), debuglength, fillchar));
            logger.debug(StringUtils.leftPad("# executing Scenario: " + scenario.getName(), debuglength, fillchar));
            logger.debug(StringUtils.leftPad(fillchar, debuglength, fillchar));



        });

        BeforeStep(scenario -> {
            testDataContainer.incrementStepIndex();
        });

        AfterStep(scenario -> {
            if (scenario.isFailed()) {
                logger.info("AfterStep");
            }
        });

        After(scenario -> {
            logger.info("AfterAll Once");

            String testId = testDataContainer.getTestId();

            if (scenario.isFailed()) {
                TakesScreenshot takesScreenshot = ((TakesScreenshot) getCurrentWebDriver());
                scenario.attach(takesScreenshot.getScreenshotAs(OutputType.BYTES), "image/png", "Screenshot");
                scenario.attach(getCurrentWebDriver().getPageSource().getBytes(), "text/html", "Page Sourcecode");

/*
                if (isMobile) {
                    //mobileScreenshot
                }
                else {
                    //desktopscreenshot
                }

*/
                //processing forced screenshots during test
                testDataContainer.getScreenshots().forEach(screenshot -> {
                    scenario.attach(screenshot.getLeft(), "image/png", "Forced Screenshot " + screenshot.getRight());

                });

                WebDriver webDriver = getCurrentWebDriverSession().getWebDriver();
                if (testDataContainer.isLocalRun()) {
                    File screenshot = ((TakesScreenshot) webDriver).getScreenshotAs(OutputType.FILE);
                    FileUtils.copyFile(screenshot, new File(System.getProperty("user.dir") + "/target/error_selenium_" + testId + "_" + testDataContainer.getCurrentSessionId() + ".png"));
                } else {
                    byte[] screenshot = ((TakesScreenshot) webDriver).getScreenshotAs(OutputType.BYTES);
                    scenario.attach(screenshot, "image/png", "Screenshot");
                }




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
