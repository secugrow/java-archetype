#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.webdriversession;

import ${package}.pageobjects.AbstractPage;
import ${package}.webdriversession.webdriverfactory.DriverFactory;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.Optional;

import static ${package}.utils.Constants.WEBDRIVER_TIMEOUT_DURATION;
import static ${package}.utils.Constants.WEBDRIVER_TIMEOUT_SECONDS;

public class WebDriverSession {

    AbstractPage currentPage;
    AbstractPage previousPage;
    WebDriver webDriver;
    WebDriverWait wdwait;

    public WebDriverSession(String sessionKey) {
        webDriver = DriverFactory.createWebDriver(sessionKey);
        wdwait = new WebDriverWait(webDriver, WEBDRIVER_TIMEOUT_DURATION);
    }

    public WebDriver getWebDriver() {
        return webDriver;
    }

    public WebDriverWait getWebDriverWait() {
        return wdwait;
    }

    public void close() {

        webDriver.quit();
        currentPage = null;
    }

    public void gotoUrl(String url) {
        webDriver.navigate().to(url);
    }


    public <T extends AbstractPage> Optional<T> getPage(Class<T> pageClass) {
        AbstractPage page = getCurrentPage();

        if (pageClass.isInstance(page)) {
            return Optional.of(pageClass.cast(page));
        } else {
            System.out.print("Page is not a " + pageClass + " actually found " + currentPage.getClass());
            return Optional.empty();
        }
    }

    public AbstractPage setCurrentPage(AbstractPage page) {
        this.previousPage = currentPage;
        this.currentPage = page;

        return page;
    }

    public AbstractPage getCurrentPage() {
        return currentPage;
    }
}
