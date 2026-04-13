package com.squareup.okhttp.internal;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/* compiled from: OptionalMethod */
public class g<T> {
    private final Class<?> a;
    private final String b;
    private final Class[] c;

    public g(Class<?> returnType, String methodName, Class... methodParams) {
        this.a = returnType;
        this.b = methodName;
        this.c = methodParams;
    }

    public boolean g(T target) {
        return a(target.getClass()) != null;
    }

    public Object d(T target, Object... args) {
        Method m = a(target.getClass());
        if (m == null) {
            return null;
        }
        try {
            return m.invoke(target, args);
        } catch (IllegalAccessException e) {
            return null;
        }
    }

    public Object e(T target, Object... args) {
        try {
            return d(target, args);
        } catch (InvocationTargetException e) {
            Throwable targetException = e.getTargetException();
            if (targetException instanceof RuntimeException) {
                throw ((RuntimeException) targetException);
            }
            AssertionError error = new AssertionError("Unexpected exception");
            error.initCause(targetException);
            throw error;
        }
    }

    public Object c(T target, Object... args) {
        Method m = a(target.getClass());
        if (m != null) {
            try {
                return m.invoke(target, args);
            } catch (IllegalAccessException e) {
                AssertionError error = new AssertionError("Unexpectedly could not call: " + m);
                error.initCause(e);
                throw error;
            }
        } else {
            throw new AssertionError("Method " + this.b + " not supported for object " + target);
        }
    }

    public Object f(T target, Object... args) {
        try {
            return c(target, args);
        } catch (InvocationTargetException e) {
            Throwable targetException = e.getTargetException();
            if (targetException instanceof RuntimeException) {
                throw ((RuntimeException) targetException);
            }
            AssertionError error = new AssertionError("Unexpected exception");
            error.initCause(targetException);
            throw error;
        }
    }

    private Method a(Class<?> clazz) {
        Class<?> cls;
        String str = this.b;
        if (str == null) {
            return null;
        }
        Method method = b(clazz, str, this.c);
        if (method == null || (cls = this.a) == null || cls.isAssignableFrom(method.getReturnType())) {
            return method;
        }
        return null;
    }

    private static Method b(Class<?> clazz, String methodName, Class[] parameterTypes) {
        try {
            Method method = clazz.getMethod(methodName, parameterTypes);
            if ((method.getModifiers() & 1) == 0) {
                return null;
            }
            return method;
        } catch (NoSuchMethodException e) {
            return null;
        }
    }
}
