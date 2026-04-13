package org.spongycastle.util;

import java.math.BigInteger;
import java.security.AccessControlException;
import java.security.AccessController;
import java.security.PrivilegedAction;
import java.util.Map;

public class Properties {
    /* access modifiers changed from: private */
    public static final ThreadLocal a = new ThreadLocal();

    private Properties() {
    }

    public static boolean d(String propertyName) {
        try {
            String p = c(propertyName);
            if (p != null) {
                return "true".equals(Strings.h(p));
            }
            return false;
        } catch (AccessControlException e) {
            return false;
        }
    }

    public static BigInteger b(String propertyName) {
        String p = c(propertyName);
        if (p != null) {
            return new BigInteger(p);
        }
        return null;
    }

    private static String c(final String propertyName) {
        return (String) AccessController.doPrivileged(new PrivilegedAction() {
            public Object run() {
                Map localProps = (Map) Properties.a.get();
                if (localProps != null) {
                    return localProps.get(propertyName);
                }
                return System.getProperty(propertyName);
            }
        });
    }
}
