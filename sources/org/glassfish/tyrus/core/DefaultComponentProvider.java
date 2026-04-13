package org.glassfish.tyrus.core;

import java.lang.reflect.Method;

public class DefaultComponentProvider extends ComponentProvider {
    public boolean isApplicable(Class<?> cls) {
        return true;
    }

    public <T> Object create(Class<T> toLoad) {
        try {
            return ReflectionHelper.getInstance(toLoad);
        } catch (Exception e) {
            return null;
        }
    }

    public boolean destroy(Object o) {
        return false;
    }

    public Method getInvocableMethod(Method method) {
        return method;
    }
}
