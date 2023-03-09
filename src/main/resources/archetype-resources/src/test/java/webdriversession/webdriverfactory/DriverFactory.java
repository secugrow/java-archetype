#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.webdriversession.webdriverfactory;

import org.openqa.selenium.WebDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Locale;

import static org.junit.Assert.fail;

public class DriverFactory {

    public Logger logger = LoggerFactory.getLogger(this.getClass());

    public static WebDriver createWebDriver(String sessionName) {
        WebDriver webDriver = null;
        String browserName = System.getProperty("browser", DriverType.CHROME.toString());
        DriverType driverType = DriverType.valueOf(browserName.toUpperCase(Locale.ROOT));

        switch (driverType) {
            case CHROME:
                webDriver = new ChromeDriverFactory().createDriver();
                break;
            case CHROME_MOBILE_EMULATION:
                webDriver = new ChromeDriverMobileEmulationFactory().createDriver();
                break;
            case FIREFOX:
                webDriver = new FirefoxWebDriverFactory().createDriver();
                break;
            case EDGE:
                fail("not implemented yet");
                break;
            case IE:
                fail("not implemented yet");
                break;
            case REMOTE_CHROME:
                webDriver = new RemoteChromeDriverFactory().createDriver();
                break;
            case REMOTE_FIREFOX:
                webDriver = new RemoteFirefoxDriverFactory().createDriver();
                break;
            case REMOTE_ANDROID:
                fail("not implemented yet");
                break;
            case APPIUM_ANDROID_DEVICE:
                webDriver = new AppiumAndroidWebDriverFactory().createDriver();
                break;
            case APPIUM_IOS_DEVICE:
                fail("not implemented yet");
                break;
        }


        return webDriver;
    }



}
