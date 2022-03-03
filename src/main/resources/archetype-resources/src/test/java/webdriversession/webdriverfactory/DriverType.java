#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.webdriversession.webdriverfactory;

public enum DriverType {
    CHROME,
    CHROME_MOBILE_EMULATION,
    FIREFOX,
    OPERA,
    EDGE,
    IE,
    REMOTE_CHROME,
    REMOTE_FIREFOX,
    REMOTE_ANDROID,
    APPIUM_ANDROID_DEVICE,
    APPIUM_IOS_DEVICE
}
