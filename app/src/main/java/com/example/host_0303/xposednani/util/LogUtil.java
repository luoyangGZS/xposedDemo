package com.example.host_0303.xposednani.util;


import de.robv.android.xposed.XposedBridge;

public class LogUtil {
    private static final boolean DEBUG = true;
    public static void log(String logMessage){
        if (DEBUG)
            XposedBridge.log(logMessage);
    }
}
