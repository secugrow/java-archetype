#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.pageobjects;

import ${package}.webdriversession.WebDriverSession;

public class MainPage extends AbstractPage {

    public MainPage(WebDriverSession session) {
        super(session);
    }
}
