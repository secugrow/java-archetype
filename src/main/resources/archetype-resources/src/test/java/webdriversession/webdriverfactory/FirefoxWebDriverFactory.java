#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.webdriversession.webdriverfactory;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class FirefoxWebDriverFactory extends WebDriverFactory {

    private static final Logger LOGGER = LoggerFactory.getLogger(FirefoxWebDriverFactory.class);

    public WebDriver createDriver() {
        FirefoxOptions ffOptions = new FirefoxOptions();
        boolean linux = System.getProperty("os.name").toLowerCase().contains("linux");
        if (linux && System.getenv().get("TMPDIR").contains("could_be_necessary_for_firefox_linux")) {
            LOGGER.warn("If you running on Linux and using a snap installed firefox you have to set the TMPDIR to a writeable directory in your homedirectory with the absolute path!");
        }
        return new FirefoxDriver(ffOptions.merge(caps));
    }
}
