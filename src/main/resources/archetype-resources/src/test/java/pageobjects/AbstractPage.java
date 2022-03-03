#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.pageobjects;

import ${package}.webdriversession.WebDriverSession;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AbstractPage {

    public Logger logger = LoggerFactory.getLogger(this.getClass());

    protected final WebDriverSession session;
    Boolean isAndroidDriver = false;
    Boolean isNativeAndroid = false;

    public AbstractPage(WebDriverSession session) {
        this.session = session;
        session.setCurrentPage(this);
    }

    public WebDriver getWebDriver() {
        return session.getWebDriver();
    }

    public WebDriverWait getWebDriverWait() {
        return session.getWebDriverWait();
    }

}
