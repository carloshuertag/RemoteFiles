package com.chuertag.practica1;

import java.io.File;

/**
 *
 * @author chuertag
 */
public class RemoteFilesProperties {
    public static final int PORT = 8000;
    public static final String SERVER_IPADDR = "127.0.0.1";
    public static String SERVER_DIRECTORY = "/remote";
    public static String CLIENT_DIRECTORY = "/local";
    public static String SLASH = "/";
    public static final String CURRENT_ABSOLUTE_PATH = System.getProperty("user.dir");
    public static void windows() {
        SERVER_DIRECTORY = "\\remote";
        CLIENT_DIRECTORY = "\\local";
        SLASH = "\\";
    }
}
