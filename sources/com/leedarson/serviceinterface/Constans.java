package com.leedarson.serviceinterface;

import androidx.exifinterface.media.ExifInterface;
import com.meituan.robust.ChangeQuickRedirect;
import java.util.concurrent.atomic.AtomicBoolean;

public class Constans {
    public static String IPC_LIVE_TYPE_KVS = "1";
    public static String IPC_LIVE_TYPE_KVS_AND_LDS = ExifInterface.GPS_MEASUREMENT_3D;
    public static String IPC_LIVE_TYPE_LDS = ExifInterface.GPS_MEASUREMENT_2D;
    public static String IPC_LIVE_TYPE_TUTK = "0";
    public static final String NOTIFIBROAD_ACTION_NAME = "android.intent.action.NotificationBroadcastReceiver";
    public static final String NOTIFI_ACTION_NAME = "android.intent.action.NotificationReceiver";
    public static final int REQUEST_CODE_ALEXA = 1000;
    public static final int REQUEST_CODE_FILE_CHOOSER = 111;
    public static String VIPLevel = "";
    public static boolean appLogin = false;
    public static String hostname = "127.0.0.1";
    public static boolean isDidRender = false;
    public static boolean isDidUnhandler = false;
    public static AtomicBoolean isLogin = null;
    public static boolean isScreenOn = true;
    public static boolean isVIP = false;
    public static long launchHttpServer = 0;
    public static long loadH52DidRender = 0;
    public static long onCreate2loadH5 = 0;
    public static String push_data;
    public static String userId = "";
    public static String userName = "";

    public enum DEVICETYPE {
        ;
        
        public static ChangeQuickRedirect changeQuickRedirect;
    }
}
