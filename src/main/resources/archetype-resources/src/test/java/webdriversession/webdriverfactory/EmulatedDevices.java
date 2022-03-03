#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.webdriversession.webdriverfactory;

public enum EmulatedDevices {
    Galaxy_Note_3("Galaxy Note 3"),
    Nexus_7("Nexus 7"),
    Nexus_10("Nexus 10"),
    Galaxy_S5("Galaxy S5"),
    Pixel_2("Pixel 2"),
    Pixel_2_XL("Pixel 2 XL"),
    iPhone_4("iPhone 4"),
    iPhone_5_SE("iPhone 5/SE"),
    iPhone_6_7_8("iPhone 6/7/8"),
    iPhone_6_7_8_Plus("iPhone 6/7/8 Plus"),
    iPhone_X("iPhone X"),
    iPad("iPad"),
    iPad_Mini("iPad Mini"),
    iPad_Pro("iPad Pro");

    public final String phoneName;

    EmulatedDevices(String phoneName) {
        this.phoneName = phoneName;
    }
}
