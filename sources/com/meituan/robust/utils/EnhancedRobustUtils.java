package com.meituan.robust.utils;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

public class EnhancedRobustUtils {
    public static boolean isThrowable = true;

    public static Object invokeReflectConstruct(String className, Object[] parameter, Class[] args) {
        try {
            Constructor constructor = Class.forName(className).getDeclaredConstructor(args);
            constructor.setAccessible(true);
            return constructor.newInstance(parameter);
        } catch (Exception e) {
            e.printStackTrace();
            if (!isThrowable) {
                return null;
            }
            throw new RuntimeException("invokeReflectConstruct error " + className + "   parameter   " + parameter);
        }
    }

    public static Object invokeReflectMethod(String methodName, Object targetObject, Object[] parameters, Class[] args, Class declaringClass) {
        try {
            return getDeclaredMethod(targetObject, methodName, args, declaringClass).invoke(targetObject, parameters);
        } catch (Exception e) {
            e.printStackTrace();
            if (!isThrowable) {
                return null;
            }
            throw new RuntimeException("invokeReflectMethod error " + methodName + "   parameter   " + parameters + " targetObject " + targetObject.toString() + "  args  " + args);
        }
    }

    public static Method getDeclaredMethod(Object object, String methodName, Class[] parameterTypes, Class declaringClass) {
        if (declaringClass == null || !declaringClass.isInterface()) {
            Class cls = object.getClass();
            while (cls != null) {
                try {
                    Method method = cls.getDeclaredMethod(methodName, parameterTypes);
                    if (method != null) {
                        if (!method.isAccessible()) {
                            method.setAccessible(true);
                        }
                        return method;
                    }
                    cls = cls.getSuperclass();
                } catch (Exception e) {
                }
            }
        } else {
            try {
                return declaringClass.getDeclaredMethod(methodName, parameterTypes);
            } catch (Exception e2) {
            }
        }
        if (!isThrowable) {
            return null;
        }
        throw new RuntimeException("getDeclaredMethod error " + methodName + "   parameterTypes   " + parameterTypes + " targetObject " + object.toString());
    }

    public static Object invokeReflectStaticMethod(String methodName, Class cl, Object[] parameter, Class[] args) {
        try {
            Method method = cl.getDeclaredMethod(methodName, args);
            method.setAccessible(true);
            return method.invoke((Object) null, parameter);
        } catch (Exception e) {
            e.printStackTrace();
            if (!isThrowable) {
                return null;
            }
            throw new RuntimeException("invokeReflectStaticMethod error " + methodName + "   class   " + cl + "  args  " + args);
        }
    }

    public static void setFieldValue(String name, Object instance, int value, Class cl) {
        try {
            getReflectField(name, instance, cl).setInt(instance, value);
        } catch (Exception e) {
            e.printStackTrace();
            if (isThrowable) {
                throw new RuntimeException("setFieldValue int error " + name + "   target   " + instance + "  value  " + value);
            }
        }
    }

    public static void setFieldValue(String name, Object instance, boolean value, Class cl) {
        try {
            getReflectField(name, instance, cl).setBoolean(instance, value);
        } catch (Exception e) {
            e.printStackTrace();
            if (isThrowable) {
                throw new RuntimeException("setFieldValue boolean error " + name + "   target   " + instance + "  value  " + value);
            }
        }
    }

    public static void setFieldValue(String name, Object instance, byte value, Class cl) {
        try {
            getReflectField(name, instance, cl).setByte(instance, value);
        } catch (Exception e) {
            e.printStackTrace();
            if (isThrowable) {
                throw new RuntimeException("setFieldValue byte error " + name + "   target   " + instance + "  value  " + value);
            }
        }
    }

    public static void setFieldValue(String name, Object instance, char value, Class cl) {
        try {
            getReflectField(name, instance, cl).setChar(instance, value);
        } catch (Exception e) {
            e.printStackTrace();
            if (isThrowable) {
                throw new RuntimeException("setFieldValue char error " + name + "   target   " + instance + "  value  " + value);
            }
        }
    }

    public static void setFieldValue(String name, Object instance, double value, Class cl) {
        try {
            getReflectField(name, instance, cl).setDouble(instance, value);
        } catch (Exception e) {
            e.printStackTrace();
            if (isThrowable) {
                throw new RuntimeException("setFieldValue double error " + name + "   target   " + instance + "  value  " + value);
            }
        }
    }

    public static void setFieldValue(String name, Object instance, float value, Class cl) {
        try {
            getReflectField(name, instance, cl).setFloat(instance, value);
        } catch (Exception e) {
            e.printStackTrace();
            if (isThrowable) {
                throw new RuntimeException("setFieldValue float error " + name + "   target   " + instance + "  value  " + value);
            }
        }
    }

    public static void setFieldValue(String name, Object instance, long value, Class cl) {
        try {
            getReflectField(name, instance, cl).setLong(instance, value);
        } catch (Exception e) {
            e.printStackTrace();
            if (isThrowable) {
                throw new RuntimeException("setFieldValue long error " + name + "   target   " + instance + "  value  " + value);
            }
        }
    }

    public static void setFieldValue(String name, Object instance, Object value, Class cl) {
        try {
            getReflectField(name, instance, cl).set(instance, value);
        } catch (Exception e) {
            e.printStackTrace();
            if (isThrowable) {
                throw new RuntimeException("setFieldValue Object error " + name + "   target   " + instance + "  value  " + value);
            }
        }
    }

    public static void setFieldValue(String name, Object instance, short value, Class cl) {
        try {
            getReflectField(name, instance, cl).setShort(instance, value);
        } catch (Exception e) {
            e.printStackTrace();
            if (isThrowable) {
                throw new RuntimeException("setFieldValue short error " + name + "   target   " + instance + "  value  " + value);
            }
        }
    }

    public static void setStaticFieldValue(String name, Class clazz, Object value) {
        try {
            getReflectStaticField(name, clazz).set((Object) null, value);
        } catch (Exception e) {
            e.printStackTrace();
            if (isThrowable) {
                throw new RuntimeException("setStaticFieldValue Object error " + name + "   Class   " + clazz + "  value  " + value);
            }
        }
    }

    public static void setStaticFieldValue(String name, Class clazz, int value) {
        try {
            getReflectStaticField(name, clazz).setInt((Object) null, value);
        } catch (Exception e) {
            e.printStackTrace();
            if (isThrowable) {
                throw new RuntimeException("setStaticFieldValue int error " + name + "   Class   " + clazz + "  value  " + value);
            }
        }
    }

    public static void setStaticFieldValue(String name, Class clazz, boolean value) {
        try {
            getReflectStaticField(name, clazz).setBoolean((Object) null, value);
        } catch (Exception e) {
            e.printStackTrace();
            if (isThrowable) {
                throw new RuntimeException("setStaticFieldValue boolean error " + name + "   Class   " + clazz + "  value  " + value);
            }
        }
    }

    public static void setStaticFieldValue(String name, Class clazz, double value) {
        try {
            getReflectStaticField(name, clazz).setDouble((Object) null, value);
        } catch (Exception e) {
            e.printStackTrace();
            if (isThrowable) {
                throw new RuntimeException("setStaticFieldValue Object error " + name + "   Class   " + clazz + "  value  " + value);
            }
        }
    }

    public static void setStaticFieldValue(String name, Class clazz, float value) {
        try {
            getReflectStaticField(name, clazz).setFloat((Object) null, value);
        } catch (Exception e) {
            e.printStackTrace();
            if (isThrowable) {
                throw new RuntimeException("setStaticFieldValue float error " + name + "   Class   " + clazz + "  value  " + value);
            }
        }
    }

    public static void setStaticFieldValue(String name, Class clazz, long value) {
        try {
            getReflectStaticField(name, clazz).setLong((Object) null, value);
        } catch (Exception e) {
            e.printStackTrace();
            if (isThrowable) {
                throw new RuntimeException("setStaticFieldValue long error " + name + "   Class   " + clazz + "  value  " + value);
            }
        }
    }

    public static Object getFieldValue(String name, Object instance, Class cl) {
        try {
            return getReflectField(name, instance, cl).get(instance);
        } catch (Exception e) {
            e.printStackTrace();
            if (!isThrowable) {
                return null;
            }
            throw new RuntimeException("getFieldValue  error " + name + "   instance   " + instance);
        }
    }

    private static Field getReflectField(String name, Object instance, Class cl) {
        if (cl == null) {
            if (!isThrowable) {
                return null;
            }
            throw new RuntimeException("Field " + name + " declaring class is null ");
        } else if (cl.isInterface()) {
            return cl.getDeclaredField(name);
        } else {
            try {
                Field field = cl.getDeclaredField(name);
                if (!field.isAccessible()) {
                    field.setAccessible(true);
                }
                return field;
            } catch (NoSuchFieldException e) {
                throw new NoSuchFieldException("Field " + name + " not found in " + instance.getClass());
            }
        }
    }

    public static Object getStaticFieldValue(String name, Class clazz) {
        try {
            return getReflectStaticField(name, clazz).get((Object) null);
        } catch (Exception e) {
            e.printStackTrace();
            if (!isThrowable) {
                return null;
            }
            throw new RuntimeException("getStaticFieldValue  error " + name + "   clazz   " + clazz);
        }
    }

    private static Field getReflectStaticField(String name, Class clazz) {
        try {
            Field field = clazz.getDeclaredField(name);
            if (!field.isAccessible()) {
                field.setAccessible(true);
            }
            return field;
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
            throw new NoSuchFieldException("Field " + name + " not found in " + clazz);
        }
    }
}
