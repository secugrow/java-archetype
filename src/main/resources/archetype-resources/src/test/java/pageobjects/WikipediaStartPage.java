#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.pageobjects;

import ${package}.webdriversession.WebDriverSession;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class WikipediaStartPage extends MainPage {

    public WikipediaStartPage(WebDriverSession session) {
        super(session);
    }

    public WebElement getSearchbar() {
        return getWebDriverWait().until(ExpectedConditions.presenceOfElementLocated(By.id("txtSearch")));
    }

    public WikiPediaSearchresultPage submitSearch() {
        getWebDriverWait().until(ExpectedConditions.elementToBeClickable(By.id("cmdSearch"))).click();
        return new WikiPediaSearchresultPage(session);
    }

    public WikipediaContentPage searchFor(String searchstring) {
        getSearchbar().sendKeys(searchstring);
        submitSearch();
        return new WikipediaContentPage(session);
    }


}