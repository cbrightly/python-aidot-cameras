package com.squareup.moshi;

import java.io.ObjectInputStream;
import java.io.ObjectStreamClass;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/* compiled from: ClassFactory */
public abstract class b<T> {
    /* access modifiers changed from: package-private */
    public abstract T b();

    b() {
    }

    public static <T> b<T> a(Class<?> rawType) {
        try {
            Constructor<?> constructor = rawType.getDeclaredConstructor(new Class[0]);
            constructor.setAccessible(true);
            return new a(constructor, rawType);
        } catch (NoSuchMethodException e) {
            try {
                Class<?> unsafeClass = Class.forName("sun.misc.Unsafe");
                Field f = unsafeClass.getDeclaredField("theUnsafe");
                f.setAccessible(true);
                return new C0205b(unsafeClass.getMethod("allocateInstance", new Class[]{Class.class}), f.get((Object) null), rawType);
            } catch (IllegalAccessException e2) {
                throw new AssertionError();
            } catch (ClassNotFoundException | NoSuchFieldException | NoSuchMethodException e3) {
                try {
                    Method getConstructorId = ObjectStreamClass.class.getDeclaredMethod("getConstructorId", new Class[]{Class.class});
                    getConstructorId.setAccessible(true);
                    int constructorId = ((Integer) getConstructorId.invoke((Object) null, new Object[]{Object.class})).intValue();
                    Method newInstance = ObjectStreamClass.class.getDeclaredMethod("newInstance", new Class[]{Class.class, Integer.TYPE});
                    newInstance.setAccessible(true);
                    return new c(newInstance, rawType, constructorId);
                } catch (IllegalAccessException e4) {
                    throw new AssertionError();
                } catch (InvocationTargetException e5) {
                    throw com.squareup.moshi.internal.b.q(e5);
                } catch (NoSuchMethodException e6) {
                    try {
                        Method newInstance2 = ObjectInputStream.class.getDeclaredMethod("newInstance", new Class[]{Class.class, Class.class});
                        newInstance2.setAccessible(true);
                        return new d(newInstance2, rawType);
                    } catch (Exception e7) {
                        throw new IllegalArgumentException("cannot construct instances of " + rawType.getName());
                    }
                }
            }
        }
    }

    /* compiled from: ClassFactory */
    public class a extends b<T> {
        final /* synthetic */ Constructor a;
        final /* synthetic */ Class b;

        a(Constructor constructor, Class cls) {
            this.a = constructor;
            this.b = cls;
        }

        public T b() {
            return this.a.newInstance((Object[]) null);
        }

        public String toString() {
            return this.b.getName();
        }
    }

    /* renamed from: com.squareup.moshi.b$b  reason: collision with other inner class name */
    /* compiled from: ClassFactory */
    public class C0205b extends b<T> {
        final /* synthetic */ Method a;
        final /* synthetic */ Object b;
        final /* synthetic */ Class c;

        C0205b(Method method, Object obj, Class cls) {
            this.a = method;
            this.b = obj;
            this.c = cls;
        }

        public T b() {
            return this.a.invoke(this.b, new Object[]{this.c});
        }

        public String toString() {
            return this.c.getName();
        }
    }

    /* compiled from: ClassFactory */
    public class c extends b<T> {
        final /* synthetic */ Method a;
        final /* synthetic */ Class b;
        final /* synthetic */ int c;

        c(Method method, Class cls, int i) {
            this.a = method;
            this.b = cls;
            this.c = i;
        }

        public T b() {
            return this.a.invoke((Object) null, new Object[]{this.b, Integer.valueOf(this.c)});
        }

        public String toString() {
            return this.b.getName();
        }
    }

    /* compiled from: ClassFactory */
    public class d extends b<T> {
        final /* synthetic */ Method a;
        final /* synthetic */ Class b;

        d(Method method, Class cls) {
            this.a = method;
            this.b = cls;
        }

        public T b() {
            return this.a.invoke((Object) null, new Object[]{this.b, Object.class});
        }

        public String toString() {
            return this.b.getName();
        }
    }
}
