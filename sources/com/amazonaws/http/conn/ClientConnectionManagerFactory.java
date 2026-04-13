package com.amazonaws.http.conn;

import com.amazonaws.logging.Log;
import com.amazonaws.logging.LogFactory;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import org.apache.http.conn.b;
import org.apache.http.conn.e;

public class ClientConnectionManagerFactory {
    /* access modifiers changed from: private */
    public static final Log log = LogFactory.getLog(ClientConnectionManagerFactory.class);

    public static b wrap(b orig) {
        if (!(orig instanceof Wrapped)) {
            return (b) Proxy.newProxyInstance(ClientConnectionManagerFactory.class.getClassLoader(), new Class[]{b.class, Wrapped.class}, new Handler(orig));
        }
        throw new IllegalArgumentException();
    }

    public static class Handler implements InvocationHandler {
        private final b orig;

        Handler(b real) {
            this.orig = real;
        }

        public Object invoke(Object proxy, Method method, Object[] args) {
            try {
                Object ret = method.invoke(this.orig, args);
                return ret instanceof e ? ClientConnectionRequestFactory.wrap((e) ret) : ret;
            } catch (InvocationTargetException e) {
                ClientConnectionManagerFactory.log.debug("", e);
                throw e.getCause();
            }
        }
    }
}
