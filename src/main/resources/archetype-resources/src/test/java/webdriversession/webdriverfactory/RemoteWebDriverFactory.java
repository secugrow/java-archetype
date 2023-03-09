#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.webdriversession.webdriverfactory;

import org.openqa.selenium.Platform;
import org.openqa.selenium.WebDriver;

import java.util.Map;

public class RemoteWebDriverFactory extends WebDriverFactory {

    WebDriver webDriver;
    String videoRecording = System.getProperty("videoRecording", "false");

    protected void setBasicCaps() {
        caps.setVersion(getBrowserVersion());
        caps.setPlatform(Platform.LINUX);
        caps.setCapability("sessionTimeout", "10m");
        caps.setCapability("enableVNC", true);
        caps.setCapability("name", getBranchName());
        caps.setCapability("timeZone", getModifiedTimeZone());

        String providerName = System.getProperty("remote.options", "selenoid");
        String executionTag = System.getProperty("execution.tag", "exection.tag not set");

        Map<String, Object> providerOptions() = Map<String, Object>of(
                "name", executionTag,
                "enableVNC", true
                //FIXME more are missing here
        );

        caps.setCapability(providerName + ":options", providerOptions);



        if (videoRecording.equals("true")) {
            //log.info("Video recording is enabled");
            caps.setCapability("enableVideo", true);
            caps.setCapability("videoName", getVideoPrefix() + "${LocalDateTime.now()}.mp4");
            caps.setCapability("videoCodec", "mpeg4");
        }
    }

    private String getVideoPrefix() {
        return "VIDEO_RECORDING_";
    }
    private String getModifiedTimeZone(){
        return System.getProperty("timezone", "Europe/Vienna");
    }
    protected String getRemoteTestingServer(){
        return System.getProperty("selenium.grid", "http://localhost:4444");
    }
    String getBranchName(){
        return System.getProperty("testbranch", "debug_run");
    }

}
