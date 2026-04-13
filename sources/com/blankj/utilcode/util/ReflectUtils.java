package com.blankj.utilcode.util;

import java.lang.reflect.AccessibleObject;
import java.lang.reflect.Field;
import java.lang.reflect.Member;
import java.lang.reflect.Modifier;

public final class ReflectUtils {
    private final Class<?> a;
    private final Object b;

    private ReflectUtils(Class<?> type, Object object) {
        this.a = type;
        this.b = object;
    }

    public static ReflectUtils g(Object object) {
        return new ReflectUtils(object == null ? Object.class : object.getClass(), object);
    }

    public ReflectUtils b(String name) {
        try {
            Field field = f(name);
            return new ReflectUtils(field.getType(), field.get(this.b));
        } catch (IllegalAccessException e) {
            throw new ReflectException((Throwable) e);
        }
    }

    public ReflectUtils c(String name, Object value) {
        try {
            f(name).set(this.b, i(value));
            return this;
        } catch (Exception e) {
            throw new ReflectException((Throwable) e);
        }
    }

    private Field f(String name) {
        Field field = e(name);
        if ((field.getModifiers() & 16) == 16) {
            try {
                Field modifiersField = Field.class.getDeclaredField("modifiers");
                modifiersField.setAccessible(true);
                modifiersField.setInt(field, field.getModifiers() & -17);
            } catch (NoSuchFieldException e) {
                field.setAccessible(true);
            }
        }
        return field;
    }

    private Field e(String name) {
        Class h = h();
        try {
            return (Field) a(h.getField(name));
        } catch (NoSuchFieldException e) {
            do {
                try {
                    return (Field) a(h.getDeclaredField(name));
                } catch (NoSuchFieldException e2) {
                    h = h.getSuperclass();
                    if (h != null) {
                        throw new ReflectException((Throwable) e);
                    }
                }
            } while (h != null);
            throw new ReflectException((Throwable) e);
        }
    }

    private Object i(Object object) {
        if (object instanceof ReflectUtils) {
            return ((ReflectUtils) object).d();
        }
        return object;
    }

    private <T extends AccessibleObject> T a(T accessible) {
        if (accessible == null) {
            return null;
        }
        if (accessible instanceof Member) {
            Member member = (Member) accessible;
            if (Modifier.isPublic(member.getModifiers()) && Modifier.isPublic(member.getDeclaringClass().getModifiers())) {
                return accessible;
            }
        }
        if (!accessible.isAccessible()) {
            accessible.setAccessible(true);
        }
        return accessible;
    }

    private Class<?> h() {
        return this.a;
    }

    public <T> T d() {
        return this.b;
    }

    public int hashCode() {
        return this.b.hashCode();
    }

    public boolean equals(Object obj) {
        return (obj instanceof ReflectUtils) && this.b.equals(((ReflectUtils) obj).d());
    }

    public String toString() {
        return this.b.toString();
    }

    public static class ReflectException extends RuntimeException {
        private static final long serialVersionUID = 858774075258496016L;

        public ReflectException(String message) {
            super(message);
        }

        public ReflectException(String message, Throwable cause) {
            super(message, cause);
        }

        public ReflectException(Throwable cause) {
            super(cause);
        }
    }
}
