package com.sensorsdata.analytics.android.sdk.util;

import android.annotation.SuppressLint;
import android.os.Build;
import android.util.LruCache;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.HashSet;
import java.util.Set;

public class ReflectUtil {
    @SuppressLint({"NewApi"})
    private static final LruCache<String, Class<?>> mObjectLruCache = new LruCache<>(64);
    private static final Set<String> mObjectSet = new HashSet();

    static <T> T findField(Class<?> clazz, Object instance, String... fieldName) {
        Field field = findFieldObj(clazz, fieldName);
        if (field == null) {
            return null;
        }
        try {
            return field.get(instance);
        } catch (IllegalAccessException e) {
            return null;
        } catch (Exception e2) {
            return null;
        }
    }

    public static <T> T findField(String[] className, Object instance, String... fieldName) {
        Class<?> currentClass = getCurrentClass(className);
        if (currentClass != null) {
            return findField(currentClass, instance, fieldName);
        }
        return null;
    }

    public static Class<?> getCurrentClass(String[] className) {
        if (className == null || className.length == 0) {
            return null;
        }
        Class<?> currentClass = null;
        for (int i = 0; i < className.length; i++) {
            try {
                int i2 = Build.VERSION.SDK_INT;
                if (i2 >= 12) {
                    currentClass = mObjectLruCache.get(className[i]);
                }
                if (currentClass == null && !mObjectSet.contains(className[i])) {
                    currentClass = Class.forName(className[i]);
                    if (i2 >= 12) {
                        mObjectLruCache.put(className[i], currentClass);
                    }
                }
            } catch (Throwable th) {
                currentClass = null;
                mObjectSet.add(className[i]);
            }
            if (currentClass != null) {
                break;
            }
        }
        return currentClass;
    }

    public static Class<?> getClassByName(String name) {
        Class<?> compatClass = null;
        try {
            int i = Build.VERSION.SDK_INT;
            if (i >= 12) {
                compatClass = mObjectLruCache.get(name);
            }
            if (compatClass == null && !mObjectSet.contains(name)) {
                compatClass = Class.forName(name);
                if (i >= 12) {
                    mObjectLruCache.put(name, compatClass);
                }
            }
            return compatClass;
        } catch (ClassNotFoundException e) {
            mObjectSet.add(name);
            return null;
        } catch (Throwable th) {
            return null;
        }
    }

    public static boolean isInstance(Object object, String... args) {
        if (args == null || args.length == 0) {
            return false;
        }
        Class clazz = null;
        boolean result = false;
        for (String arg : args) {
            try {
                int i = Build.VERSION.SDK_INT;
                if (i >= 12) {
                    clazz = mObjectLruCache.get(arg);
                }
                if (clazz == null && !mObjectSet.contains(arg)) {
                    clazz = Class.forName(arg);
                    if (i >= 12) {
                        mObjectLruCache.put(arg, clazz);
                    }
                }
                if (clazz != null) {
                    result = clazz.isInstance(object);
                }
            } catch (Throwable th) {
                mObjectSet.add(arg);
            }
            if (result) {
                break;
            }
        }
        return result;
    }

    public static <T> T callMethod(Object instance, String methodName, Object... args) {
        Class[] argsClass = new Class[args.length];
        for (int i = 0; i < args.length; i++) {
            argsClass[i] = args[i].getClass();
        }
        Method method = getMethod(instance.getClass(), methodName, argsClass);
        if (method == null) {
            return null;
        }
        try {
            return method.invoke(instance, args);
        } catch (Exception e) {
            return null;
        }
    }

    public static <T> T callStaticMethod(Class<?> clazz, String methodName, Object... args) {
        if (clazz == null) {
            return null;
        }
        Class[] argsClass = new Class[args.length];
        for (int i = 0; i < args.length; i++) {
            argsClass[i] = args[i].getClass();
        }
        Method method = getMethod(clazz, methodName, argsClass);
        if (method != null) {
            try {
                return method.invoke((Object) null, args);
            } catch (Exception e) {
            }
        }
        return null;
    }

    static Method getDeclaredRecur(Class<?> cls, String methodName, Class<?>... params) {
        Class<?> clazz;
        while (true) {
            Class<?> clazz2 = cls;
            if (clazz2 == Object.class) {
                return null;
            }
            try {
                Method method = clazz2.getDeclaredMethod(methodName, params);
                if (method != null) {
                    return method;
                }
                clazz = clazz2;
                clazz2 = clazz;
            } catch (NoSuchMethodException e) {
                clazz = clazz2.getSuperclass();
            }
        }
    }

    static Method getMethod(Class<?> clazz, String methodName, Class<?>... params) {
        try {
            return clazz.getMethod(methodName, params);
        } catch (NoSuchMethodException e) {
            return null;
        }
    }

    static Field findFieldObj(Class<?> clazz, String... fieldName) {
        if (fieldName != null) {
            try {
                if (fieldName.length != 0) {
                    Field field = null;
                    for (int i = 0; i < fieldName.length; i++) {
                        try {
                            field = clazz.getDeclaredField(fieldName[i]);
                        } catch (NoSuchFieldException e) {
                            field = null;
                        }
                        if (field != null) {
                            break;
                        }
                    }
                    if (field == null) {
                        return null;
                    }
                    field.setAccessible(true);
                    return field;
                }
            } catch (Exception e2) {
                return null;
            }
        }
        return null;
    }

    static Field findFieldObjRecur(Class<?> cls, String fieldName) {
        while (true) {
            Class<? super Object> current = cls;
            if (current == Object.class) {
                return null;
            }
            try {
                Field field = current.getDeclaredField(fieldName);
                field.setAccessible(true);
                return field;
            } catch (NoSuchFieldException e) {
                current = current.getSuperclass();
            }
        }
    }

    static <T> T findFieldRecur(Object instance, String fieldName) {
        Field field = findFieldObjRecur(instance.getClass(), fieldName);
        if (field == null) {
            return null;
        }
        try {
            return field.get(instance);
        } catch (IllegalAccessException e) {
            return null;
        }
    }
}
