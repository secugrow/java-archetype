#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.webdriversession.webdriverfactory;

import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.remote.MobileCapabilityType;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.chrome.ChromeOptions;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;

import static org.assertj.core.api.Assertions.fail;

public class AppiumAndroidWebDriverFactory extends AppiumDriverFactory {

    WebDriver webDriver;

    public WebDriver createDriver() {

        WebDriverManager.chromedriver().driverVersion(super.getWebDriverVersion()).setup();
        String path2chromeDriver = WebDriverManager.chromedriver().getDownloadedDriverPath();


        ChromeOptions chromeOptions = new ChromeOptions();
        chromeOptions.setExperimentalOption("w3c", false);

        caps.setCapability("chromedriverExecutableDir", path2chromeDriver.substring(0,path2chromeDriver.lastIndexOf(File.separator)));
        caps.setCapability(ChromeOptions.CAPABILITY, chromeOptions);
        caps.setCapability(MobileCapabilityType.PLATFORM_NAME, "Android");
        caps.setCapability(MobileCapabilityType.DEVICE_NAME, "Appium_Android_Device");
        caps.setCapability(MobileCapabilityType.AUTOMATION_NAME, "UiAutomator2");

        caps.setCapability(MobileCapabilityType.UDID, getMobileDeviceId());
        String appiumServerString = super.getSeleniumGrid();

        try {
            URL appiumServer = new URL(appiumServerString + "/wd/hub");
            webDriver = new AndroidDriver(appiumServer, caps);
        } catch (WebDriverException e) {
            fail("Appium error: " + appiumServerString + " exception message: " + e + " ::: Appium started?");
        } catch (MalformedURLException malformedURLException) {
            fail ("URL for AppiumServer is malformed " + appiumServerString + "${symbol_escape}n " + malformedURLException);
        }
        return webDriver;

    }


}


