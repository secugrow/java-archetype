#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.webdriversession.webdriverfactory;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import java.util.HashMap;
import java.util.Map;

import static ${package}.webdriversession.webdriverfactory.EmulatedDevices.Pixel_2;

public class ChromeDriverMobileEmulationFactory extends WebDriverFactory {

    WebDriver webDriver;


    public WebDriver createDriver() {

        ChromeOptions options = new ChromeOptions();

        Map<String, String> mobileEmulation = new HashMap<>();
        String deviceName = System.getProperty("emulated.device",  Pixel_2.phoneName);
        mobileEmulation.put("deviceName", deviceName);
        options.setExperimentalOption("mobileEmulation", mobileEmulation);

        this.webDriver = new ChromeDriver(options.merge(caps));


        return webDriver;

    }
}
