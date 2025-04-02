#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.webdriversession.webdriverfactory;

import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.options.UiAutomator2Options;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.fail;

public class AppiumAndroidWebDriverFactory extends AppiumDriverFactory {

    WebDriver webDriver;

    public WebDriver createDriver() {

        UiAutomator2Options uiAutomator2Options = new UiAutomator2Options();
        uiAutomator2Options.setUdid(getMobileDeviceId());;
        uiAutomator2Options.setAutomationName("UiAutomator2");
        uiAutomator2Options.setPlatformName("Android");
        uiAutomator2Options.setDeviceName("Appium_Android_Device");
        uiAutomator2Options.withBrowserName("chrome");
        uiAutomator2Options.setNoReset(true);
        uiAutomator2Options.setCapability(
                "chomeOptions", Map.of(
                        "args", List.of("disable-extensions", "--no-sandbox"),
                        "w3c" ,false
                )
        );

        String appiumServerString = super.getSeleniumGrid();

        try {
            URL appiumServer = new URI(appiumServerString).toURL();
            webDriver = new AndroidDriver(appiumServer, uiAutomator2Options);
        } catch (WebDriverException e) {
            fail("Appium error: " + appiumServerString + " exception message: " + e + " ::: Appium started?");
        } catch (MalformedURLException | URISyntaxException uriException) {
            fail("URL for AppiumServer is malformed " + appiumServerString + "\n " + uriException);
        }
        return webDriver;

    }


}


