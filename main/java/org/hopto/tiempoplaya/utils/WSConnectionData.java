package org.hopto.tiempoplaya.utils;

public abstract class WSConnectionData {

    //final static String PROTOCOL = "http"; //http or https
    //final static String HOST = "192.168.0.173:8080"; //host name or ip

    final static String PROTOCOL = "https"; //http or https
    final static String HOST = "tiempoyplaya.com"; //host name or ip

    public static String getPROTOCOL() {
        return PROTOCOL;
    }

    public static String getHOST() {
        return HOST;
    }
}
