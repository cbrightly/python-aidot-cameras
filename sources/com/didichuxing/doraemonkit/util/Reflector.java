package com.didichuxing.doraemonkit.util;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Member;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;

public class Reflector {
    public static final String TAG = "Reflector";
    protected Object mCaller;
    protected Constructor mConstructor;
    protected Field mField;
    protected Method mMethod;
    protected Class<?> mType;

    public static class ReflectedException extends Exception {
        public ReflectedException(String message) {
            super(message);
        }

        public ReflectedException(String message, Throwable cause) {
            super(message, cause);
        }
    }

    public static Reflector on(@NonNull String name) {
        return on(name, true, Reflector.class.getClassLoader());
    }

    public static Reflector on(@NonNull String name, boolean initialize) {
        return on(name, initialize, Reflector.class.getClassLoader());
    }

    public static Reflector on(@NonNull String name, boolean initialize, @Nullable ClassLoader loader) {
        try {
            return on(Class.forName(name, initialize, loader));
        } catch (Throwable e) {
            throw new ReflectedException("Oops!", e);
        }
    }

    public static Reflector on(@NonNull Class<?> type) {
        Reflector reflector = new Reflector();
        reflector.mType = type;
        return reflector;
    }

    public static Reflector with(@NonNull Object caller) {
        return on(caller.getClass()).bind(caller);
    }

    protected Reflector() {
    }

    public Reflector constructor(@Nullable Class<?>... parameterTypes) {
        try {
            Constructor<?> declaredConstructor = this.mType.getDeclaredConstructor(parameterTypes);
            this.mConstructor = declaredConstructor;
            declaredConstructor.setAccessible(true);
            this.mField = null;
            this.mMethod = null;
            return this;
        } catch (Throwable e) {
            throw new ReflectedException("Oops!", e);
        }
    }

    public <R> R newInstance(@Nullable Object... initargs) {
        Constructor constructor = this.mConstructor;
        if (constructor != null) {
            try {
                return constructor.newInstance(initargs);
            } catch (InvocationTargetException e) {
                throw new ReflectedException("Oops!", e.getTargetException());
            } catch (Throwable e2) {
                throw new ReflectedException("Oops!", e2);
            }
        } else {
            throw new ReflectedException("Constructor was null!");
        }
    }

    /* access modifiers changed from: protected */
    public Object checked(@Nullable Object caller) {
        if (caller == null || this.mType.isInstance(caller)) {
            return caller;
        }
        throw new ReflectedException("Caller [" + caller + "] is not a instance of type [" + this.mType + "]!");
    }

    /* access modifiers changed from: protected */
    public void check(@Nullable Object caller, @Nullable Member member, @NonNull String name) {
        if (member == null) {
            throw new ReflectedException(name + " was null!");
        } else if (caller != null || Modifier.isStatic(member.getModifiers())) {
            checked(caller);
        } else {
            throw new ReflectedException("Need a caller!");
        }
    }

    public Reflector bind(@Nullable Object caller) {
        this.mCaller = checked(caller);
        return this;
    }

    public Reflector unbind() {
        this.mCaller = null;
        return this;
    }

    public Reflector field(@NonNull String name) {
        try {
            Field findField = findField(name);
            this.mField = findField;
            findField.setAccessible(true);
            this.mConstructor = null;
            this.mMethod = null;
            return this;
        } catch (Throwable e) {
            throw new ReflectedException("Oops!", e);
        }
    }

    /* access modifiers changed from: protected */
    public Field findField(@NonNull String name) {
        try {
            return this.mType.getField(name);
        } catch (NoSuchFieldException e) {
            Class cls = this.mType;
            while (cls != null) {
                try {
                    return cls.getDeclaredField(name);
                } catch (NoSuchFieldException e2) {
                    cls = cls.getSuperclass();
                }
            }
            throw e;
        }
    }

    public <R> R get() {
        return get(this.mCaller);
    }

    public <R> R get(@Nullable Object caller) {
        check(caller, this.mField, "Field");
        try {
            return this.mField.get(caller);
        } catch (Throwable e) {
            throw new ReflectedException("Oops!", e);
        }
    }

    public Reflector set(@Nullable Object value) {
        return set(this.mCaller, value);
    }

    public Reflector set(@Nullable Object caller, @Nullable Object value) {
        check(caller, this.mField, "Field");
        try {
            this.mField.set(caller, value);
            return this;
        } catch (Throwable e) {
            throw new ReflectedException("Oops!", e);
        }
    }

    public Reflector method(@NonNull String name, @Nullable Class<?>... parameterTypes) {
        try {
            Method findMethod = findMethod(name, parameterTypes);
            this.mMethod = findMethod;
            findMethod.setAccessible(true);
            this.mConstructor = null;
            this.mField = null;
            return this;
        } catch (NoSuchMethodException e) {
            throw new ReflectedException("Oops!", e);
        }
    }

    /* access modifiers changed from: protected */
    public Method findMethod(@NonNull String name, @Nullable Class<?>... parameterTypes) {
        try {
            return this.mType.getMethod(name, parameterTypes);
        } catch (NoSuchMethodException e) {
            Class cls = this.mType;
            while (cls != null) {
                try {
                    return cls.getDeclaredMethod(name, parameterTypes);
                } catch (NoSuchMethodException e2) {
                    cls = cls.getSuperclass();
                }
            }
            throw e;
        }
    }

    public <R> R call(@Nullable Object... args) {
        return callByCaller(this.mCaller, args);
    }

    public <R> R callByCaller(@Nullable Object caller, @Nullable Object... args) {
        check(caller, this.mMethod, "Method");
        try {
            return this.mMethod.invoke(caller, args);
        } catch (InvocationTargetException e) {
            throw new ReflectedException("Oops!", e.getTargetException());
        } catch (Throwable e2) {
            throw new ReflectedException("Oops!", e2);
        }
    }

    public static class QuietReflector extends Reflector {
        protected Throwable mIgnored;

        public static QuietReflector on(@NonNull String name) {
            return on(name, true, QuietReflector.class.getClassLoader());
        }

        public static QuietReflector on(@NonNull String name, boolean initialize) {
            return on(name, initialize, QuietReflector.class.getClassLoader());
        }

        public static QuietReflector on(@NonNull String name, boolean initialize, @Nullable ClassLoader loader) {
            try {
                return on(Class.forName(name, initialize, loader), (Throwable) null);
            } catch (Throwable e) {
                return on((Class<?>) null, e);
            }
        }

        public static QuietReflector on(@Nullable Class<?> type) {
            return on(type, (Throwable) type == null ? new ReflectedException("Type was null!") : null);
        }

        private static QuietReflector on(@Nullable Class<?> type, @Nullable Throwable ignored) {
            QuietReflector reflector = new QuietReflector();
            reflector.mType = type;
            reflector.mIgnored = ignored;
            return reflector;
        }

        public static QuietReflector with(@Nullable Object caller) {
            if (caller == null) {
                return on((Class<?>) null);
            }
            return on(caller.getClass()).bind(caller);
        }

        protected QuietReflector() {
        }

        public Throwable getIgnored() {
            return this.mIgnored;
        }

        /* access modifiers changed from: protected */
        public boolean skip() {
            return skipAlways() || this.mIgnored != null;
        }

        /* access modifiers changed from: protected */
        public boolean skipAlways() {
            return this.mType == null;
        }

        public QuietReflector constructor(@Nullable Class<?>... parameterTypes) {
            if (skipAlways()) {
                return this;
            }
            try {
                this.mIgnored = null;
                Reflector.super.constructor(parameterTypes);
            } catch (Throwable e) {
                this.mIgnored = e;
            }
            return this;
        }

        public <R> R newInstance(@Nullable Object... initargs) {
            if (skip()) {
                return null;
            }
            try {
                this.mIgnored = null;
                return Reflector.super.newInstance(initargs);
            } catch (Throwable e) {
                this.mIgnored = e;
                return null;
            }
        }

        public QuietReflector bind(@Nullable Object obj) {
            if (skipAlways()) {
                return this;
            }
            try {
                this.mIgnored = null;
                Reflector.super.bind(obj);
            } catch (Throwable e) {
                this.mIgnored = e;
            }
            return this;
        }

        public QuietReflector unbind() {
            Reflector.super.unbind();
            return this;
        }

        public QuietReflector field(@NonNull String name) {
            if (skipAlways()) {
                return this;
            }
            try {
                this.mIgnored = null;
                Reflector.super.field(name);
            } catch (Throwable e) {
                this.mIgnored = e;
            }
            return this;
        }

        public <R> R get() {
            if (skip()) {
                return null;
            }
            try {
                this.mIgnored = null;
                return Reflector.super.get();
            } catch (Throwable e) {
                this.mIgnored = e;
                return null;
            }
        }

        public <R> R get(@Nullable Object caller) {
            if (skip()) {
                return null;
            }
            try {
                this.mIgnored = null;
                return Reflector.super.get(caller);
            } catch (Throwable e) {
                this.mIgnored = e;
                return null;
            }
        }

        public QuietReflector set(@Nullable Object value) {
            if (skip()) {
                return this;
            }
            try {
                this.mIgnored = null;
                Reflector.super.set(value);
            } catch (Throwable e) {
                this.mIgnored = e;
            }
            return this;
        }

        public QuietReflector set(@Nullable Object caller, @Nullable Object value) {
            if (skip()) {
                return this;
            }
            try {
                this.mIgnored = null;
                Reflector.super.set(caller, value);
            } catch (Throwable e) {
                this.mIgnored = e;
            }
            return this;
        }

        public QuietReflector method(@NonNull String name, @Nullable Class<?>... parameterTypes) {
            if (skipAlways()) {
                return this;
            }
            try {
                this.mIgnored = null;
                Reflector.super.method(name, parameterTypes);
            } catch (Throwable e) {
                this.mIgnored = e;
            }
            return this;
        }

        public <R> R call(@Nullable Object... args) {
            if (skip()) {
                return null;
            }
            try {
                this.mIgnored = null;
                return Reflector.super.call(args);
            } catch (Throwable e) {
                this.mIgnored = e;
                return null;
            }
        }

        public <R> R callByCaller(@Nullable Object caller, @Nullable Object... args) {
            if (skip()) {
                return null;
            }
            try {
                this.mIgnored = null;
                return Reflector.super.callByCaller(caller, args);
            } catch (Throwable e) {
                this.mIgnored = e;
                return null;
            }
        }
    }
}
