#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.webdriversession.webdriverfactory;

import org.openqa.selenium.Dimension;

public enum ScreenDimension {
    Desktop_1080(new Dimension(1920, 1080)),
    Desktop_720(new Dimension(1280, 720)),
    Desktop_w930(new Dimension(930, 720)),
    Desktop_w929(new Dimension(929, 720)),
    Desktop_w767(new Dimension(767, 720)),
    Phone_small(new Dimension(320, 640)),
    Phone_medium(new Dimension(576, 768)),
    Phone_large(new Dimension(1200, 1980)),
    Tablet_small(new Dimension(768, 576)),
    Pixel_2(new Dimension(411, 731));

    ScreenDimension(Dimension dimension) {

    }
}
