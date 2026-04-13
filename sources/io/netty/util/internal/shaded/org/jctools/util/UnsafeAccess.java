package io.netty.util.internal.shaded.org.jctools.util;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import sun.misc.Unsafe;

public class UnsafeAccess {
    public static final boolean SUPPORTS_GET_AND_SET;
    public static final Unsafe UNSAFE;

    static {
        Field field;
        Class<Object> cls = Object.class;
        try {
            Field field2 = Unsafe.class.getDeclaredField("theUnsafe");
            field2.setAccessible(true);
            field = (Unsafe) field2.get((Object) null);
        } catch (Exception e) {
            try {
                Constructor<Unsafe> c = Unsafe.class.getDeclaredConstructor(new Class[0]);
                c.setAccessible(true);
                field = (Unsafe) c.newInstance(new Object[0]);
            } catch (Exception e2) {
                SUPPORTS_GET_AND_SET = false;
                throw new RuntimeException(e2);
            }
        }
        boolean getAndSetSupport = false;
        Class<Unsafe> cls2 = Unsafe.class;
        try {
            cls2.getMethod("getAndSetObject", new Class[]{cls, Long.TYPE, cls});
            getAndSetSupport = true;
        } catch (Exception e3) {
        }
        UNSAFE = field;
        SUPPORTS_GET_AND_SET = getAndSetSupport;
    }
}
