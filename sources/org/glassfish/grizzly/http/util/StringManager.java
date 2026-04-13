package org.glassfish.grizzly.http.util;

import com.google.maps.android.BuildConfig;
import java.text.MessageFormat;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

public class StringManager {
    private static final Map<String, StringManager> managers = new HashMap();
    private ResourceBundle bundle;

    private StringManager(String packageName, ClassLoader loader) {
        this(packageName, Locale.getDefault(), loader);
    }

    private StringManager(String packageName, Locale loc, ClassLoader loader) {
        String bundleName = packageName + ".LocalStrings";
        try {
            this.bundle = ResourceBundle.getBundle(bundleName, loc, loader);
        } catch (MissingResourceException e) {
            this.bundle = ResourceBundle.getBundle(bundleName, Locale.US, loader);
        }
    }

    private StringManager(ResourceBundle bundle2) {
        this.bundle = bundle2;
    }

    public String getString(String key) {
        if (key != null) {
            try {
                return this.bundle.getString(key);
            } catch (MissingResourceException e) {
                return null;
            }
        } else {
            throw new IllegalArgumentException("key may not have a null value");
        }
    }

    public String getString(String key, Object[] args) {
        String value = getString(key);
        if (args == null) {
            try {
                args = new Object[1];
            } catch (IllegalArgumentException e) {
                StringBuilder buf = new StringBuilder();
                buf.append(value);
                for (int i = 0; i < args.length; i++) {
                    buf.append(" arg[");
                    buf.append(i);
                    buf.append("]=");
                    buf.append(args[i]);
                }
                return buf.toString();
            }
        }
        Object[] nonNullArgs = args;
        for (int i2 = 0; i2 < args.length; i2++) {
            if (args[i2] == null) {
                if (nonNullArgs == args) {
                    nonNullArgs = (Object[]) args.clone();
                }
                nonNullArgs[i2] = BuildConfig.TRAVIS;
            }
        }
        if (value == null) {
            value = key;
        }
        return MessageFormat.format(value, nonNullArgs);
    }

    public String getString(String key, Object arg) {
        return getString(key, new Object[]{arg});
    }

    public String getString(String key, Object arg1, Object arg2) {
        return getString(key, new Object[]{arg1, arg2});
    }

    public String getString(String key, Object arg1, Object arg2, Object arg3) {
        return getString(key, new Object[]{arg1, arg2, arg3});
    }

    public String getString(String key, Object arg1, Object arg2, Object arg3, Object arg4) {
        return getString(key, new Object[]{arg1, arg2, arg3, arg4});
    }

    public static synchronized StringManager getManager(String packageName, ClassLoader loader) {
        StringManager mgr;
        synchronized (StringManager.class) {
            Map<String, StringManager> map = managers;
            mgr = map.get(packageName);
            if (mgr == null) {
                mgr = new StringManager(packageName, loader);
                map.put(packageName, mgr);
            }
        }
        return mgr;
    }

    public static synchronized StringManager getManager(ResourceBundle bundle2) {
        StringManager stringManager;
        synchronized (StringManager.class) {
            stringManager = new StringManager(bundle2);
        }
        return stringManager;
    }

    public static synchronized StringManager getManager(String packageName, Locale loc, ClassLoader loader) {
        StringManager mgr;
        synchronized (StringManager.class) {
            Map<String, StringManager> map = managers;
            mgr = map.get(packageName + '_' + loc.toString());
            if (mgr == null) {
                mgr = new StringManager(packageName, loc, loader);
                map.put(packageName + '_' + loc.toString(), mgr);
            }
        }
        return mgr;
    }
}
