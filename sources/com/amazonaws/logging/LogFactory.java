package com.amazonaws.logging;

import android.util.Log;
import java.util.HashMap;
import java.util.Map;

public class LogFactory {
    private static final String APACHE_COMMONS_LOGGING_LOGFACTORY = "org.apache.commons.logging.LogFactory";
    private static final String TAG = LogFactory.class.getSimpleName();
    private static Level globalLogLevel = null;
    private static Map<String, Log> logMap = new HashMap();

    public static synchronized Log getLog(Class clazz) {
        Log log;
        synchronized (LogFactory.class) {
            log = getLog(getTruncatedLogTag(clazz.getSimpleName()));
        }
        return log;
    }

    public static synchronized Log getLog(String logTag) {
        Log log;
        synchronized (LogFactory.class) {
            String logTag2 = getTruncatedLogTag(logTag);
            log = logMap.get(logTag2);
            if (log == null) {
                if (checkApacheCommonsLoggingExists()) {
                    try {
                        log = new ApacheCommonsLogging(logTag2);
                        logMap.put(logTag2, log);
                    } catch (Exception e) {
                        Log.w(TAG, "Could not create log from org.apache.commons.logging.LogFactory", e);
                    }
                }
                if (log == null) {
                    log = new AndroidLog(logTag2);
                    logMap.put(logTag2, log);
                }
            }
        }
        return log;
    }

    public static void setLevel(Level level) {
        globalLogLevel = level;
    }

    public static Level getLevel() {
        return globalLogLevel;
    }

    private static boolean checkApacheCommonsLoggingExists() {
        try {
            Class<?> cls = Class.forName("org.apache.commons.logging.h");
            return true;
        } catch (ClassNotFoundException e) {
            return false;
        } catch (Exception ex) {
            Log.e(TAG, ex.getMessage());
            return false;
        }
    }

    private static String getTruncatedLogTag(String logTag) {
        if (logTag.length() <= 23) {
            return logTag;
        }
        if (checkApacheCommonsLoggingExists()) {
            new ApacheCommonsLogging(TAG).warn("Truncating log tag length as it exceed 23, the limit imposed by Android on certain API Levels");
        } else {
            Log.w(TAG, "Truncating log tag length as it exceed 23, the limit imposed by Android on certain API Levels");
        }
        return logTag.substring(0, 23);
    }

    public enum Level {
        ALL(Integer.MIN_VALUE),
        TRACE(0),
        DEBUG(1),
        INFO(2),
        WARN(3),
        ERROR(4),
        OFF(Integer.MAX_VALUE);
        
        private int value;

        public int getValue() {
            return this.value;
        }

        private Level(int value2) {
            this.value = value2;
        }
    }
}
