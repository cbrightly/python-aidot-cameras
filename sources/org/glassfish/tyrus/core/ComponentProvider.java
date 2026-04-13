package org.glassfish.tyrus.core;

import java.lang.reflect.Method;

public abstract class ComponentProvider {
    public abstract <T> Object create(Class<T> cls);

    public abstract boolean destroy(Object obj);

    public abstract boolean isApplicable(Class<?> cls);

    public Method getInvocableMethod(Method method) {
        return method;
    }
}
