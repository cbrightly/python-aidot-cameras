package com.didichuxing.doraemonkit.aop;

import java.util.Map;

public class DokitPluginConfig {
    public static int STRATEGY_NORMAL = 1;
    public static int STRATEGY_NULL = -1;
    public static int STRATEGY_STACK = 0;
    public static boolean SWITCH_BIG_IMG = false;
    public static boolean SWITCH_DOKIT_PLUGIN = false;
    public static boolean SWITCH_GPS = false;
    public static boolean SWITCH_METHOD = false;
    public static boolean SWITCH_NETWORK = false;
    private static final String TAG = "DokitPluginConfig";
    public static int VALUE_METHOD_STRATEGY = -1;

    public static void inject(Map config) {
        SWITCH_DOKIT_PLUGIN = ((Boolean) config.get("dokitPluginSwitch")).booleanValue();
        SWITCH_METHOD = ((Boolean) config.get("methodSwitch")).booleanValue();
        SWITCH_BIG_IMG = ((Boolean) config.get("bigImgSwitch")).booleanValue();
        SWITCH_NETWORK = ((Boolean) config.get("networkSwitch")).booleanValue();
        SWITCH_GPS = ((Boolean) config.get("gpsSwitch")).booleanValue();
        VALUE_METHOD_STRATEGY = ((Integer) config.get("methodStrategy")).intValue();
    }
}
