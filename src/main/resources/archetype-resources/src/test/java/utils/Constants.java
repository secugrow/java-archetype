#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.utils;

import java.time.Duration;

public class Constants {

    public static final long WEBDRIVER_TIMEOUT_SECONDS = 10;
    public static final Duration WEBDRIVER_TIMEOUT_DURATION = Duration.ofSeconds(10);
}
