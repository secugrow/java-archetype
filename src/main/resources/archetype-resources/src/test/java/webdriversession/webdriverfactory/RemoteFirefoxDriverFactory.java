#set($symbol_pound='#')
#set($symbol_dollar='$')
#set($symbol_escape='\' )
package ${package}.webdriversession.webdriverfactory;

import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;

import org.openqa.selenium.Platform;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.RemoteWebDriver;

public class RemoteFirefoxDriverFactory extends RemoteWebDriverFactory {

    public WebDriver createDriver() {

        FirefoxOptions options = new FirefoxOptions();
        options.setCapability(CapabilityType.BROWSER_NAME, "firefox");
        options.setCapability(CapabilityType.BROWSER_VERSION, getBrowserVersion());
        options.setCapability(CapabilityType.PLATFORM_NAME, Platform.LINUX);

        URL gridServer = null;
        try {
            gridServer = URI.create(getRemoteTestingServer() + "/wd/hub").toURL();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        return new RemoteWebDriver(gridServer, options.merge(caps));
    }
}