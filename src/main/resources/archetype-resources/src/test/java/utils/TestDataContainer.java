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
        return getAs(Keys.TEST_ID.getKeyValue(), String.class);
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

    public String getBaseUrl() {
        return getAs(Keys.BASEURL.getKeyValue(), String.class);
    }

    public void setTestDataString(String key, String stringValue) {
        testDataMap.put(key, stringValue);
    }

    public void setTestDataString(Keys key, String stringValue) {
        testDataMap.put(key.getKeyValue(), stringValue);
    }
}
