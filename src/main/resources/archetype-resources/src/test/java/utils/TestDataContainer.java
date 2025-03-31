#set($symbol_pound='#')
#set($symbol_dollar='$')
#set($symbol_escape='\' )
package ${package}.utils;

import ${package}.webdriversession.webdriverfactory.DriverType;
import io.cucumber.java8.Scenario;
import org.apache.commons.lang3.tuple.Pair;
import org.assertj.core.api.SoftAssertions;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static org.assertj.core.api.Fail.fail;

public class TestDataContainer {

    public enum Keys {
        BASEURL("baseURL"),
        TEST_ID("testId"),
        SCREENSHOTS("screenshots"),
        LOCALRUN("localRun"),
        //a11y-start
        SKIP_A11Y("skipA11y"),
        //a11y-end
        INITIALIZED("initialized"),
        STEP_INDEX("stepIndex"),
        SOFTASSERTIONS_ACTIVE("softAssertions.present"),
        SCENARIO("scenario");

        private final String key;

        Keys(String valkey) {
            this.key = valkey;
        }

        public String getKeyValue() {
            return key;
        }
    }

    HashMap<String, Object> testDataMap;

    public TestDataContainer() {
        testDataMap = new HashMap<>();
        testDataMap.put(Keys.TEST_ID.getKeyValue(), "init");
    }

    public String getTestId() {
        return getAs(Keys.TEST_ID.getKeyValue(), String.class);
    }

    public void setScenario(Scenario scenario) {
        testDataMap.put(Keys.SCENARIO.key, scenario);
        testDataMap.put(Keys.TEST_ID.key, extractTestIdFromScenarioName(scenario));
    }

    //a11y-start
    public boolean doA11YCheck() {
        return !getAs(Keys.SKIP_A11Y.key, Boolean.class);
    }
    //a11y-end

    public void incrementStepIndex() {
        int stepIndex = getAs("stepIndex", Integer.class);
        testDataMap.put("stepIndex", stepIndex++);
    }

    public <T> T getAs(String key, Class<T> type) {
        if (testDataMap.containsKey(key)) {
            return type.cast(testDataMap.get(key));
        } else {
            throw new IllegalArgumentException("given key: " + key + " not available in testDataMap");
        }
    }

    public String getTestDataAsString(String key) {
        return getAs(key, String.class);
    }

    public Integer getTestDataAsInt(String key) {
        return getAs(key, Integer.class);
    }

    public String getCurrentSessionId() {
        return getTestDataAsString("session.id");
    }

    public ArrayList<Pair<byte[], String>> getScreenshots() {

        if (testDataMap.containsKey(Keys.SCREENSHOTS)) {
            return getAs(Keys.SCREENSHOTS.key, ArrayList.class);

        } else {
            return new ArrayList<>();
        }
    }

    public boolean isLocalRun() {
        return getAs(Keys.LOCALRUN.key, Boolean.class);
    }

    public String getBaseUrl() {
        return getAs(Keys.BASEURL.getKeyValue(), String.class);
    }

    public void setTestDataString(String key, String stringValue) {
        testDataMap.put(key, stringValue);
    }

    public void setTestDataString(Keys key, String stringValue) {
        testDataMap.put(key.key, stringValue);
    }

    public void setTestDataBoolean(Keys key, boolean boolenValue) {
        testDataMap.put(key.getKeyValue(), boolenValue);
    }

    public void setTestDataInt(Keys key, int intValue) {
        testDataMap.put(key.getKeyValue(), intValue);
    }

    public void setTestDataSoftAssertion(String key, SoftAssertions softAssertions) {
        testDataMap.put(key, softAssertions);
    }

    public void setTestDataList(String key, List<String> list) {
        testDataMap.put(key, list);
    }

    public void setTestDataBrowserType(DriverType driverType) {
        testDataMap.put("browser.type", driverType);
    }

    private void setScreenshots(Keys key, List<Pair<byte[], String>> screenshots) {
        testDataMap.put(Keys.SCREENSHOTS.key, screenshots);
    }

    public Scenario getScenario() {
        return (Scenario) testDataMap.get("scenario");
    }


    public void addScreenshot(byte[] screenshot, String description) {
        ArrayList<Pair<byte[], String>> screenshots = getScreenshots();
        screenshots.add(Pair.of(screenshot, description));
        setScreenshots(Keys.SCREENSHOTS, screenshots);
    }


    //a11y-start
    public void addA11ydescription(String violationString) {
        addStringtoList("a11y.description", violationString);
    }

    public SoftAssertions getSoftAssertionObject() {
        return (SoftAssertions) testDataMap.get("softAssertion.object");
    }

    //a11y-end


    private void addStringtoList(String key, String stringToAdd) {
        if (testDataMap.containsKey(key)) {
            ArrayList<String> list = getAs(key, ArrayList.class);
            list.add(stringToAdd);
            testDataMap.put(key, list);
        }
        else {
            ArrayList<String> newList = new ArrayList<>();
            newList.add(stringToAdd);
            setTestDataList(key, newList);
        }
    }

    private String extractTestIdFromScenarioName(Scenario scenario) {
        Pattern regex = Pattern.compile("^\\[(.*?) ");
        Matcher matcher = regex.matcher(scenario.getName());

        try {
            if (matcher.find()) {
                return matcher.group(1);
            }

        } catch (NullPointerException npe) {
            fail("Scenarioname is not correct formated $scenarioName. Pattern: '[XXX-99 [Filename]");
        }
        return "invalid Scenarioname Pattern";
    }



    //a11y-start
    /*
    Missing features from kotlin archetype




    fun getScreenshots(): MutableList<Pair<ByteArray, String>> = getScreenshotArrayFromTestDataMap()

    private fun getScreenshotArrayFromTestDataMap(): MutableList<Pair<ByteArray, String>> {
        return when (testDataMap.containsKey(SCREENSHOTS)) {
            true -> getAs(SCREENSHOTS)
            false -> mutableListOf()
        }
    }



    fun hasSoftAssertions() = getAs<Boolean>("softAssertions.present")



    fun getAndClearA11Ydescriptions(): List<String> {
        val descriptions = getA11yDescription()
        testDataMap.replace("a11y.description", mutableListOf<String>())
        return descriptions
    }

    fun getA11yDescription(): List<String> {
        when (testDataMap.containsKey("a11y.description")) {
            true -> return getAs("a11y.description")
            false -> return emptyList()
        }
    }

}
     */
    //a11y-end
}
