package org.glassfish.tyrus.core;

import com.google.maps.android.BuildConfig;
import jakarta.websocket.DeploymentException;
import java.lang.reflect.AccessibleObject;
import java.lang.reflect.Array;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.GenericArrayType;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.lang.reflect.TypeVariable;
import java.security.AccessController;
import java.security.PrivilegedAction;
import java.security.PrivilegedExceptionAction;
import java.util.HashMap;
import java.util.Map;
import org.glassfish.tyrus.core.l10n.LocalizationMessages;

public class ReflectionHelper {
    public static Class getDeclaringClass(AccessibleObject ao) {
        if (ao instanceof Method) {
            return ((Method) ao).getDeclaringClass();
        }
        if (ao instanceof Field) {
            return ((Field) ao).getDeclaringClass();
        }
        if (ao instanceof Constructor) {
            return ((Constructor) ao).getDeclaringClass();
        }
        throw new RuntimeException();
    }

    public static String objectToString(Object o) {
        if (o == null) {
            return BuildConfig.TRAVIS;
        }
        return o.getClass().getName() + '@' + Integer.toHexString(o.hashCode());
    }

    public static String methodInstanceToString(Object o, Method m) {
        StringBuilder sb = new StringBuilder();
        sb.append(o.getClass().getName());
        sb.append('@');
        sb.append(Integer.toHexString(o.hashCode()));
        sb.append('.');
        sb.append(m.getName());
        sb.append('(');
        Class[] params = m.getParameterTypes();
        for (int i = 0; i < params.length; i++) {
            sb.append(getTypeName(params[i]));
            if (i < params.length - 1) {
                sb.append(",");
            }
        }
        sb.append(')');
        return sb.toString();
    }

    private static String getTypeName(Class type) {
        if (type.isArray()) {
            Class cl = type;
            int dimensions = 0;
            while (cl.isArray()) {
                try {
                    dimensions++;
                    cl = cl.getComponentType();
                } catch (Throwable th) {
                }
            }
            StringBuilder sb = new StringBuilder();
            sb.append(cl.getName());
            for (int i = 0; i < dimensions; i++) {
                sb.append("[]");
            }
            return sb.toString();
        }
        return type.getName();
    }

    public static Class classForName(String name) {
        return classForName(name, getContextClassLoader());
    }

    public static Class classForName(String name, ClassLoader cl) {
        if (cl != null) {
            try {
                return Class.forName(name, false, cl);
            } catch (ClassNotFoundException e) {
            }
        }
        try {
            return Class.forName(name);
        } catch (ClassNotFoundException e2) {
            return null;
        }
    }

    public static Class classForNameWithException(String name) {
        return classForNameWithException(name, getContextClassLoader());
    }

    public static Class classForNameWithException(String name, ClassLoader cl) {
        if (cl != null) {
            try {
                return Class.forName(name, false, cl);
            } catch (ClassNotFoundException e) {
            }
        }
        return Class.forName(name);
    }

    public static <T> PrivilegedExceptionAction<Class<T>> classForNameWithExceptionPEA(String name) {
        return classForNameWithExceptionPEA(name, getContextClassLoader());
    }

    public static <T> PrivilegedExceptionAction<Class<T>> classForNameWithExceptionPEA(final String name, final ClassLoader cl) {
        return new PrivilegedExceptionAction<Class<T>>() {
            public Class<T> run() {
                ClassLoader classLoader = cl;
                if (classLoader != null) {
                    try {
                        return Class.forName(name, false, classLoader);
                    } catch (ClassNotFoundException e) {
                    }
                }
                return Class.forName(name);
            }
        };
    }

    private static ClassLoader getContextClassLoader() {
        return (ClassLoader) AccessController.doPrivileged(getContextClassLoaderPA());
    }

    public static PrivilegedAction<ClassLoader> getContextClassLoaderPA() {
        return new PrivilegedAction<ClassLoader>() {
            public ClassLoader run() {
                return Thread.currentThread().getContextClassLoader();
            }
        };
    }

    public static void setAccessibleMethod(final Method m) {
        if (!Modifier.isPublic(m.getModifiers())) {
            AccessController.doPrivileged(new PrivilegedAction<Object>() {
                public Object run() {
                    if (!m.isAccessible()) {
                        m.setAccessible(true);
                    }
                    return m;
                }
            });
        }
    }

    public static Class getGenericClass(Type parameterizedType) {
        Type t = getTypeArgumentOfParameterizedType(parameterizedType);
        if (t == null) {
            return null;
        }
        Class c = getClassOfType(t);
        if (c != null) {
            return c;
        }
        throw new IllegalArgumentException("Type not supported");
    }

    public static final class TypeClassPair {
        public final Class c;
        public final Type t;

        public TypeClassPair(Type t2, Class c2) {
            this.t = t2;
            this.c = c2;
        }
    }

    public static TypeClassPair getTypeArgumentAndClass(Type parameterizedType) {
        Type t = getTypeArgumentOfParameterizedType(parameterizedType);
        if (t == null) {
            return null;
        }
        Class c = getClassOfType(t);
        if (c != null) {
            return new TypeClassPair(t, c);
        }
        throw new IllegalArgumentException("Generic type not supported");
    }

    private static Type getTypeArgumentOfParameterizedType(Type parameterizedType) {
        if (!(parameterizedType instanceof ParameterizedType)) {
            return null;
        }
        Type[] genericTypes = ((ParameterizedType) parameterizedType).getActualTypeArguments();
        if (genericTypes.length != 1) {
            return null;
        }
        return genericTypes[0];
    }

    private static Class getClassOfType(Type type) {
        if (type instanceof Class) {
            return (Class) type;
        }
        if (type instanceof GenericArrayType) {
            Type t = ((GenericArrayType) type).getGenericComponentType();
            if (t instanceof Class) {
                return getArrayClass((Class) t);
            }
            return null;
        } else if (!(type instanceof ParameterizedType)) {
            return null;
        } else {
            Type t2 = ((ParameterizedType) type).getRawType();
            if (t2 instanceof Class) {
                return (Class) t2;
            }
            return null;
        }
    }

    public static Class getArrayClass(Class c) {
        try {
            return Array.newInstance(c, 0).getClass();
        } catch (Exception e) {
            throw new IllegalArgumentException(e);
        }
    }

    public static Method getValueOfStringMethod(Class c) {
        try {
            Method m = c.getDeclaredMethod("valueOf", new Class[]{String.class});
            if (Modifier.isStatic(m.getModifiers()) || m.getReturnType() != c) {
                return m;
            }
            return null;
        } catch (NoSuchMethodException e) {
            return null;
        }
    }

    public static Method getFromStringStringMethod(Class c) {
        try {
            Method m = c.getDeclaredMethod("fromString", new Class[]{String.class});
            if (Modifier.isStatic(m.getModifiers()) || m.getReturnType() != c) {
                return m;
            }
            return null;
        } catch (NoSuchMethodException e) {
            return null;
        }
    }

    public static Constructor getStringConstructor(Class c) {
        try {
            return c.getConstructor(new Class[]{String.class});
        } catch (Exception e) {
            return null;
        }
    }

    public static class DeclaringClassInterfacePair {
        public final Class concreteClass;
        public final Class declaringClass;
        public final Type genericInterface;

        private DeclaringClassInterfacePair(Class concreteClass2, Class declaringClass2, Type genericInteface) {
            this.concreteClass = concreteClass2;
            this.declaringClass = declaringClass2;
            this.genericInterface = genericInteface;
        }
    }

    public static Class[] getParameterizedClassArguments(DeclaringClassInterfacePair p) {
        Type type = p.genericInterface;
        if (!(type instanceof ParameterizedType)) {
            return null;
        }
        Type[] as = ((ParameterizedType) type).getActualTypeArguments();
        Class[] cas = new Class[as.length];
        for (int i = 0; i < as.length; i++) {
            Type a = as[i];
            if (a instanceof Class) {
                cas[i] = (Class) a;
            } else if (a instanceof ParameterizedType) {
                cas[i] = (Class) ((ParameterizedType) a).getRawType();
            } else if (a instanceof TypeVariable) {
                ClassTypePair ctp = resolveTypeVariable(p.concreteClass, p.declaringClass, (TypeVariable) a);
                cas[i] = ctp != null ? ctp.c : Object.class;
            } else if (a instanceof GenericArrayType) {
                cas[i] = getClassOfType(a);
            }
        }
        return cas;
    }

    public static Type[] getParameterizedTypeArguments(DeclaringClassInterfacePair p) {
        Type type = p.genericInterface;
        if (!(type instanceof ParameterizedType)) {
            return null;
        }
        Type[] as = ((ParameterizedType) type).getActualTypeArguments();
        Type[] ras = new Type[as.length];
        for (int i = 0; i < as.length; i++) {
            Type a = as[i];
            if (a instanceof Class) {
                ras[i] = a;
            } else if (a instanceof ParameterizedType) {
                ParameterizedType pt = (ParameterizedType) a;
                ras[i] = a;
            } else if (a instanceof TypeVariable) {
                ras[i] = resolveTypeVariable(p.concreteClass, p.declaringClass, (TypeVariable) a).t;
            }
        }
        return ras;
    }

    public static DeclaringClassInterfacePair getClass(Class concrete, Class iface) {
        return getClass(concrete, iface, concrete);
    }

    private static DeclaringClassInterfacePair getClass(Class concrete, Class iface, Class c) {
        DeclaringClassInterfacePair p = getType(concrete, iface, c, c.getGenericInterfaces());
        if (p != null) {
            return p;
        }
        Class c2 = c.getSuperclass();
        if (c2 == null || c2 == Object.class) {
            return null;
        }
        return getClass(concrete, iface, c2);
    }

    private static DeclaringClassInterfacePair getType(Class concrete, Class iface, Class c, Type[] ts) {
        for (Type t : ts) {
            DeclaringClassInterfacePair p = getType(concrete, iface, c, t);
            if (p != null) {
                return p;
            }
        }
        return null;
    }

    private static DeclaringClassInterfacePair getType(Class concrete, Class iface, Class c, Type t) {
        if (t instanceof Class) {
            if (t == iface) {
                return new DeclaringClassInterfacePair(concrete, c, t);
            }
            return getClass(concrete, iface, (Class) t);
        } else if (!(t instanceof ParameterizedType)) {
            return null;
        } else {
            ParameterizedType pt = (ParameterizedType) t;
            if (pt.getRawType() == iface) {
                return new DeclaringClassInterfacePair(concrete, c, t);
            }
            return getClass(concrete, iface, (Class) pt.getRawType());
        }
    }

    public static class ClassTypePair {
        public final Class c;
        public final Type t;

        public ClassTypePair(Class c2) {
            this(c2, c2);
        }

        public ClassTypePair(Class c2, Type t2) {
            this.c = c2;
            this.t = t2;
        }
    }

    public static ClassTypePair resolveTypeVariable(Class c, Class dc, TypeVariable tv2) {
        return resolveTypeVariable(c, dc, tv2, new HashMap());
    }

    private static ClassTypePair resolveTypeVariable(Class c, Class dc, TypeVariable tv2, Map<TypeVariable, Type> map) {
        for (Type gi : c.getGenericInterfaces()) {
            if (gi instanceof ParameterizedType) {
                ParameterizedType pt = (ParameterizedType) gi;
                ClassTypePair ctp = resolveTypeVariable(pt, (Class) pt.getRawType(), dc, tv2, map);
                if (ctp != null) {
                    return ctp;
                }
            }
        }
        Type gsc = c.getGenericSuperclass();
        if (gsc instanceof ParameterizedType) {
            return resolveTypeVariable((ParameterizedType) gsc, c.getSuperclass(), dc, tv2, map);
        }
        if (gsc instanceof Class) {
            return resolveTypeVariable(c.getSuperclass(), dc, tv2, map);
        }
        return null;
    }

    private static ClassTypePair resolveTypeVariable(ParameterizedType pt, Class c, Class dc, TypeVariable tv2, Map<TypeVariable, Type> map) {
        Type[] typeArguments = pt.getActualTypeArguments();
        TypeVariable[] typeParameters = c.getTypeParameters();
        Map<TypeVariable, Type> submap = new HashMap<>();
        for (int i = 0; i < typeArguments.length; i++) {
            if (typeArguments[i] instanceof TypeVariable) {
                submap.put(typeParameters[i], map.get(typeArguments[i]));
            } else {
                submap.put(typeParameters[i], typeArguments[i]);
            }
        }
        if (c != dc) {
            return resolveTypeVariable(c, dc, tv2, submap);
        }
        Type t = submap.get(tv2);
        if (t instanceof Class) {
            return new ClassTypePair((Class) t);
        }
        if (t instanceof GenericArrayType) {
            Type t2 = ((GenericArrayType) t).getGenericComponentType();
            if (t2 instanceof Class) {
                try {
                    return new ClassTypePair(getArrayClass((Class) t2));
                } catch (Exception e) {
                    return null;
                }
            } else if (!(t2 instanceof ParameterizedType)) {
                return null;
            } else {
                Type rt = ((ParameterizedType) t2).getRawType();
                if (!(rt instanceof Class)) {
                    return null;
                }
                try {
                    return new ClassTypePair(getArrayClass((Class) rt), t2);
                } catch (Exception e2) {
                    return null;
                }
            }
        } else if (!(t instanceof ParameterizedType)) {
            return null;
        } else {
            ParameterizedType pt2 = (ParameterizedType) t;
            if (pt2.getRawType() instanceof Class) {
                return new ClassTypePair((Class) pt2.getRawType(), pt2);
            }
            return null;
        }
    }

    public static Method findMethodOnClass(Class c, Method m) {
        try {
            return c.getMethod(m.getName(), m.getParameterTypes());
        } catch (NoSuchMethodException e) {
            for (Method _m : c.getMethods()) {
                if (_m.getName().equals(m.getName()) && _m.getParameterTypes().length == m.getParameterTypes().length && compareParameterTypes(m.getGenericParameterTypes(), _m.getGenericParameterTypes())) {
                    return _m;
                }
            }
            return null;
        }
    }

    private static boolean compareParameterTypes(Type[] ts, Type[] _ts) {
        for (int i = 0; i < ts.length; i++) {
            if (!ts[i].equals(_ts[i]) && !(_ts[i] instanceof TypeVariable)) {
                return false;
            }
        }
        return true;
    }

    public static Class<?> getClassType(Class<?> inspectedClass, Class<?> superClass) {
        Class[] as = getParameterizedClassArguments(getClass(inspectedClass, superClass));
        if (as == null) {
            return null;
        }
        return as[0];
    }

    public static OsgiRegistry getOsgiRegistryInstance() {
        try {
            Class<?> cls = Class.forName("org.osgi.framework.b");
            return OsgiRegistry.getInstance();
        } catch (Exception e) {
            return null;
        }
    }

    public static <T> T getInstance(Class<T> c, ErrorCollector collector) {
        try {
            return getInstance(c);
        } catch (Exception e) {
            collector.addException(new DeploymentException(LocalizationMessages.CLASS_NOT_INSTANTIATED(c.getName()), e));
            return null;
        }
    }

    public static <T> T getInstance(Class<T> c) {
        return c.newInstance();
    }
}
