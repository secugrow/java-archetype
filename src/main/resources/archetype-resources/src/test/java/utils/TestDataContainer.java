#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.utils;

import java.util.HashMap;

public class TestDataContainer {

    public enum Keys {
        BASEURL("baseURL"),
        TEST_ID("testId");

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
        return String.valueOf(testDataMap.get(Keys.TEST_ID.getKeyValue()));
    }

    public String getTestDataAsString(String key) {
        return String.valueOf(testDataMap.get(key));
    }

    public int getTestDataAsInt(String key) {
        return Integer.parseInt(String.valueOf(testDataMap.get(key)));
    }

    public String getBaseUrl() {
        return String.valueOf(testDataMap.get(Keys.BASEURL.getKeyValue()));
    }

    public void setTestDataString(String key, String stringValue) {
        testDataMap.put(key, stringValue);
    }

    public void setTestDataString(Keys key, String stringValue) {
        testDataMap.put(key.getKeyValue(), stringValue);
    }
}
