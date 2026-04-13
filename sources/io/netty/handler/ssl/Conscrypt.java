package io.netty.handler.ssl;

import io.netty.util.internal.PlatformDependent;
import java.lang.reflect.Method;
import javax.net.ssl.SSLEngine;

public final class Conscrypt {
    private static final Class<?> CONSCRYPT_CLASS = getConscryptClass();

    static boolean isAvailable() {
        return CONSCRYPT_CLASS != null && PlatformDependent.javaVersion() >= 8;
    }

    static boolean isEngineSupported(SSLEngine engine) {
        return isAvailable() && isConscryptEngine(engine, CONSCRYPT_CLASS);
    }

    private static Class<?> getConscryptClass() {
        try {
            Class<?> conscryptClass = Class.forName("org.conscrypt.Conscrypt", true, ConscryptAlpnSslEngine.class.getClassLoader());
            getIsConscryptMethod(conscryptClass);
            return conscryptClass;
        } catch (Throwable th) {
            return null;
        }
    }

    private static boolean isConscryptEngine(SSLEngine engine, Class<?> conscryptClass) {
        try {
            return ((Boolean) getIsConscryptMethod(conscryptClass).invoke((Object) null, new Object[]{engine})).booleanValue();
        } catch (Throwable th) {
            return false;
        }
    }

    private static Method getIsConscryptMethod(Class<?> conscryptClass) {
        return conscryptClass.getMethod("isConscrypt", new Class[]{SSLEngine.class});
    }

    private Conscrypt() {
    }
}
