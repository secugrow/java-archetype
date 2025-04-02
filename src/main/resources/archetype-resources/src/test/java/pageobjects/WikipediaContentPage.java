#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.pageobjects;

import ${package}.webdriversession.WebDriverSession;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class WikipediaContentPage extends MainPage{

    public WikipediaContentPage(WebDriverSession session) {
        super(session);
    }

    public String getHeader() {
        return getWebDriverWait().until(ExpectedConditions.visibilityOfElementLocated(By.id("firstHeading"))).getText();
    }
}
