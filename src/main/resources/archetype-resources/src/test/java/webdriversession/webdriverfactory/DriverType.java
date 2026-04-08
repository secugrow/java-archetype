#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.webdriversession.webdriverfactory;

public enum DriverType {
    CHROME,
    CHROMIUM,
    CHROME_MOBILE_EMULATION,
    LOCAL_CHROME_MOBILE_EMULATION,
    REMOTE_CHROME_MOBILE,
    REMOTE_CHROME_MOBILE_EMULATION,
    FIREFOX,
    EDGE,
    REMOTE_CHROME,
    REMOTE_FIREFOX,
    REMOTE_ANDROID,
    APPIUM_ANDROID_DEVICE,
    APPIUM_IOS_DEVICE
}
