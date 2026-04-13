package org.apache.http.impl.client;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import org.apache.http.client.methods.c;
import org.apache.http.q;
import org.apache.http.util.g;

@Deprecated
/* compiled from: CloseableHttpResponseProxy */
public class j implements InvocationHandler {
    private static final Constructor<?> c;
    private final q d;

    static {
        try {
            c = Proxy.getProxyClass(j.class.getClassLoader(), new Class[]{c.class}).getConstructor(new Class[]{InvocationHandler.class});
        } catch (NoSuchMethodException ex) {
            throw new IllegalStateException(ex);
        }
    }

    j(q original) {
        this.d = original;
    }

    public void a() {
        g.a(this.d.a());
    }

    public Object invoke(Object proxy, Method method, Object[] args) {
        if (method.getName().equals("close")) {
            a();
            return null;
        }
        try {
            return method.invoke(this.d, args);
        } catch (InvocationTargetException ex) {
            Throwable cause = ex.getCause();
            if (cause != null) {
                throw cause;
            }
            throw ex;
        }
    }

    public static c b(q original) {
        try {
            return (c) c.newInstance(new Object[]{new j(original)});
        } catch (InstantiationException ex) {
            throw new IllegalStateException(ex);
        } catch (InvocationTargetException ex2) {
            throw new IllegalStateException(ex2);
        } catch (IllegalAccessException ex3) {
            throw new IllegalStateException(ex3);
        }
    }
}
