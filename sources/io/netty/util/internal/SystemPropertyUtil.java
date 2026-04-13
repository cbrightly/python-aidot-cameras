package io.netty.util.internal;

import com.didichuxing.doraemonkit.okgo.cache.CacheEntity;
import com.tencent.bugly.Bugly;
import io.netty.util.internal.logging.InternalLogger;
import io.netty.util.internal.logging.InternalLoggerFactory;
import java.security.AccessController;
import java.security.PrivilegedAction;

public final class SystemPropertyUtil {
    private static final InternalLogger logger = InternalLoggerFactory.getInstance((Class<?>) SystemPropertyUtil.class);

    public static boolean contains(String key) {
        return get(key) != null;
    }

    public static String get(String key) {
        return get(key, (String) null);
    }

    public static String get(final String key, String def) {
        if (key == null) {
            throw new NullPointerException(CacheEntity.KEY);
        } else if (!key.isEmpty()) {
            String value = null;
            try {
                if (System.getSecurityManager() == null) {
                    value = System.getProperty(key);
                } else {
                    value = (String) AccessController.doPrivileged(new PrivilegedAction<String>() {
                        public String run() {
                            return System.getProperty(key);
                        }
                    });
                }
            } catch (SecurityException e) {
                logger.warn("Unable to retrieve a system property '{}'; default values will be used.", key, e);
            }
            if (value == null) {
                return def;
            }
            return value;
        } else {
            throw new IllegalArgumentException("key must not be empty.");
        }
    }

    public static boolean getBoolean(String key, boolean def) {
        String value = get(key);
        if (value == null) {
            return def;
        }
        String value2 = value.trim().toLowerCase();
        if (value2.isEmpty()) {
            return def;
        }
        if ("true".equals(value2) || "yes".equals(value2) || "1".equals(value2)) {
            return true;
        }
        if (Bugly.SDK_IS_DEV.equals(value2) || "no".equals(value2) || "0".equals(value2)) {
            return false;
        }
        logger.warn("Unable to parse the boolean system property '{}':{} - using the default value: {}", key, value2, Boolean.valueOf(def));
        return def;
    }

    public static int getInt(String key, int def) {
        String value = get(key);
        if (value == null) {
            return def;
        }
        String value2 = value.trim();
        try {
            return Integer.parseInt(value2);
        } catch (Exception e) {
            logger.warn("Unable to parse the integer system property '{}':{} - using the default value: {}", key, value2, Integer.valueOf(def));
            return def;
        }
    }

    public static long getLong(String key, long def) {
        String value = get(key);
        if (value == null) {
            return def;
        }
        String value2 = value.trim();
        try {
            return Long.parseLong(value2);
        } catch (Exception e) {
            logger.warn("Unable to parse the long integer system property '{}':{} - using the default value: {}", key, value2, Long.valueOf(def));
            return def;
        }
    }

    private SystemPropertyUtil() {
    }
}
