#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.webdriversession;

import java.util.HashMap;

public class WebDriverSessionStore {

    private static HashMap<String, WebDriverSession> sessionStore;

    public static synchronized WebDriverSession getOrCreate(String sessionKey) {

        if (sessionStore != null) {
            if (sessionStore.containsKey(sessionKey)) {
                return sessionStore.get(sessionKey);
            }
        } else {
            sessionStore = new HashMap<>();
        }
        sessionStore.put(sessionKey, new WebDriverSession(sessionKey));
        return sessionStore.get(sessionKey);
    }

    public static WebDriverSession getIfExists(String key) {

        if (sessionStore.containsKey(key)) {
            return sessionStore.get(key);
        }
        return null;
    }

    public static void closeAll() {
        sessionStore.entrySet().stream().parallel().forEach(entry -> entry.getValue().close());
    }
}
