package com.alibaba.fastjson.util;

import androidx.exifinterface.media.ExifInterface;
import com.alibaba.fastjson.annotation.JSONField;
import com.alibaba.fastjson.asm.ClassReader;
import com.alibaba.fastjson.asm.TypeCollector;
import com.meituan.robust.Constants;
import java.io.IOException;
import java.io.InputStream;
import java.lang.annotation.Annotation;
import java.lang.reflect.AccessibleObject;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.lang.reflect.Type;

public class ASMUtils {
    public static final boolean IS_ANDROID;
    public static final String JAVA_VM_NAME;

    static {
        String property = System.getProperty("java.vm.name");
        JAVA_VM_NAME = property;
        IS_ANDROID = isAndroid(property);
    }

    public static boolean isAndroid(String vmName) {
        if (vmName == null) {
            return false;
        }
        String lowerVMName = vmName.toLowerCase();
        if (lowerVMName.contains("dalvik") || lowerVMName.contains("lemur")) {
            return true;
        }
        return false;
    }

    public static String desc(Method method) {
        Class<?>[] types = method.getParameterTypes();
        StringBuilder buf = new StringBuilder((types.length + 1) << 4);
        buf.append('(');
        for (Class<?> desc : types) {
            buf.append(desc(desc));
        }
        buf.append(')');
        buf.append(desc(method.getReturnType()));
        return buf.toString();
    }

    public static String desc(Class<?> returnType) {
        if (returnType.isPrimitive()) {
            return getPrimitiveLetter(returnType);
        }
        if (returnType.isArray()) {
            return Constants.ARRAY_TYPE + desc(returnType.getComponentType());
        }
        return "L" + type(returnType) + Constants.PACKNAME_END;
    }

    public static String type(Class<?> parameterType) {
        if (parameterType.isArray()) {
            return Constants.ARRAY_TYPE + desc(parameterType.getComponentType());
        } else if (!parameterType.isPrimitive()) {
            return parameterType.getName().replace('.', '/');
        } else {
            return getPrimitiveLetter(parameterType);
        }
    }

    public static String getPrimitiveLetter(Class<?> type) {
        if (Integer.TYPE == type) {
            return "I";
        }
        if (Void.TYPE == type) {
            return ExifInterface.GPS_MEASUREMENT_INTERRUPTED;
        }
        if (Boolean.TYPE == type) {
            return "Z";
        }
        if (Character.TYPE == type) {
            return "C";
        }
        if (Byte.TYPE == type) {
            return "B";
        }
        if (Short.TYPE == type) {
            return ExifInterface.LATITUDE_SOUTH;
        }
        if (Float.TYPE == type) {
            return "F";
        }
        if (Long.TYPE == type) {
            return "J";
        }
        if (Double.TYPE == type) {
            return "D";
        }
        throw new IllegalStateException("Type: " + type.getCanonicalName() + " is not a primitive type");
    }

    public static Type getMethodType(Class<?> clazz, String methodName) {
        try {
            return clazz.getMethod(methodName, new Class[0]).getGenericReturnType();
        } catch (Exception e) {
            return null;
        }
    }

    public static boolean checkName(String name) {
        for (int i = 0; i < name.length(); i++) {
            char c = name.charAt(i);
            if (c < 1 || c > 127 || c == '.') {
                return false;
            }
        }
        return true;
    }

    public static String[] lookupParameterNames(AccessibleObject methodOrCtor) {
        Annotation[][] parameterAnnotations;
        Class<?> declaringClass;
        String name;
        Class<?>[] types;
        ClassLoader classLoader;
        String fieldName;
        AccessibleObject accessibleObject = methodOrCtor;
        if (IS_ANDROID) {
            return new String[0];
        }
        if (accessibleObject instanceof Method) {
            Method method = (Method) accessibleObject;
            types = method.getParameterTypes();
            name = method.getName();
            declaringClass = method.getDeclaringClass();
            parameterAnnotations = TypeUtils.getParameterAnnotations(method);
        } else {
            Constructor<?> constructor = (Constructor) accessibleObject;
            types = constructor.getParameterTypes();
            declaringClass = constructor.getDeclaringClass();
            name = "<init>";
            parameterAnnotations = TypeUtils.getParameterAnnotations((Constructor) constructor);
        }
        if (types.length == 0) {
            return new String[0];
        }
        ClassLoader classLoader2 = declaringClass.getClassLoader();
        if (classLoader2 == null) {
            classLoader = ClassLoader.getSystemClassLoader();
        } else {
            classLoader = classLoader2;
        }
        String className = declaringClass.getName();
        InputStream is = classLoader.getResourceAsStream(className.replace('.', '/') + ".class");
        if (is == null) {
            return new String[0];
        }
        try {
            ClassReader reader = new ClassReader(is, false);
            TypeCollector visitor = new TypeCollector(name, types);
            reader.accept(visitor);
            String[] parameterNames = visitor.getParameterNamesForMethod();
            for (int i = 0; i < parameterNames.length; i++) {
                Annotation[] annotations = parameterAnnotations[i];
                if (annotations != null) {
                    for (int j = 0; j < annotations.length; j++) {
                        if ((annotations[j] instanceof JSONField) && (fieldName = ((JSONField) annotations[j]).name()) != null && fieldName.length() > 0) {
                            parameterNames[i] = fieldName;
                        }
                    }
                }
            }
            return parameterNames;
        } catch (IOException e) {
            return new String[0];
        } finally {
            IOUtils.close(is);
        }
    }
}
