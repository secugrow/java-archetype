#set($symbol_pound='#')
#set($symbol_dollar='$')
#set($symbol_escape='\' )
package ${package}.webdriversession.webdriverfactory;

import org.openqa.selenium.Platform;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;

public class RemoteFirefoxDriverFactory extends RemoteWebDriverFactory {

    private Logger log = LoggerFactory.getLogger(this.getClass());

    public WebDriver createDriver() {

        FirefoxOptions options = new FirefoxOptions();
        options.setBrowserVersion(getBrowserVersion());
        options.setPlatformName(Platform.LINUX.toString());

        URL gridServer = null;
        try {
            gridServer = URI.create(getRemoteTestingServer() + "/wd/hub").toURL();
        } catch (MalformedURLException e) {
            log.error("URL for RemoteTestingServer is malformed " + getRemoteTestingServer() + "\n " + e);
        }

        return new RemoteWebDriver(gridServer, options.merge(caps));
    }
}