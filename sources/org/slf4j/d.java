package org.slf4j;

import java.util.Map;
import org.slf4j.helpers.e;
import org.slf4j.helpers.i;
import org.slf4j.impl.StaticMDCBinder;
import org.slf4j.spi.a;

/* compiled from: MDC */
public class d {
    static a a;

    private static a a() {
        try {
            return StaticMDCBinder.getSingleton().getMDCA();
        } catch (NoSuchMethodError e) {
            return StaticMDCBinder.SINGLETON.getMDCA();
        }
    }

    static {
        try {
            a = a();
        } catch (NoClassDefFoundError ncde) {
            a = new e();
            String msg = ncde.getMessage();
            if (msg == null || !msg.contains("StaticMDCBinder")) {
                throw ncde;
            }
            i.c("Failed to load class \"org.slf4j.impl.StaticMDCBinder\".");
            i.c("Defaulting to no-operation MDCAdapter implementation.");
            i.c("See http://www.slf4j.org/codes.html#no_static_mdc_binder for further details.");
        } catch (Exception e) {
            i.d("MDC binding unsuccessful.", e);
        }
    }

    public static void d(String key, String val) {
        if (key != null) {
            a aVar = a;
            if (aVar != null) {
                aVar.a(key, val);
                return;
            }
            throw new IllegalStateException("MDCAdapter cannot be null. See also http://www.slf4j.org/codes.html#null_MDCA");
        }
        throw new IllegalArgumentException("key parameter cannot be null");
    }

    public static void e(String key) {
        if (key != null) {
            a aVar = a;
            if (aVar != null) {
                aVar.remove(key);
                return;
            }
            throw new IllegalStateException("MDCAdapter cannot be null. See also http://www.slf4j.org/codes.html#null_MDCA");
        }
        throw new IllegalArgumentException("key parameter cannot be null");
    }

    public static void b() {
        a aVar = a;
        if (aVar != null) {
            aVar.clear();
            return;
        }
        throw new IllegalStateException("MDCAdapter cannot be null. See also http://www.slf4j.org/codes.html#null_MDCA");
    }

    public static Map<String, String> c() {
        a aVar = a;
        if (aVar != null) {
            return aVar.b();
        }
        throw new IllegalStateException("MDCAdapter cannot be null. See also http://www.slf4j.org/codes.html#null_MDCA");
    }
}
