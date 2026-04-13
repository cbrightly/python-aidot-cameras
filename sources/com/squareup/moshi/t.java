package com.squareup.moshi;

import com.google.maps.android.BuildConfig;
import com.squareup.moshi.internal.b;
import java.lang.reflect.Array;
import java.lang.reflect.GenericArrayType;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.lang.reflect.TypeVariable;
import java.lang.reflect.WildcardType;
import java.util.Arrays;
import java.util.Collection;
import java.util.Map;
import java.util.Properties;
import javax.annotation.CheckReturnValue;
import javax.annotation.Nullable;

@CheckReturnValue
/* compiled from: Types */
public final class t {
    public static String e(String className) {
        return className.replace("$", "_") + "JsonAdapter";
    }

    public static ParameterizedType j(Type rawType, Type... typeArguments) {
        if (typeArguments.length != 0) {
            return new b.C0206b((Type) null, rawType, typeArguments);
        }
        throw new IllegalArgumentException("Missing type arguments for " + rawType);
    }

    public static GenericArrayType b(Type componentType) {
        return new b.a(componentType);
    }

    public static WildcardType k(Type bound) {
        return new b.c(bound instanceof WildcardType ? ((WildcardType) bound).getUpperBounds() : new Type[]{bound}, b.b);
    }

    public static WildcardType l(Type bound) {
        return new b.c(new Type[]{Object.class}, bound instanceof WildcardType ? ((WildcardType) bound).getLowerBounds() : new Type[]{bound});
    }

    public static Class<?> g(Type type) {
        if (type instanceof Class) {
            return (Class) type;
        }
        if (type instanceof ParameterizedType) {
            return (Class) ((ParameterizedType) type).getRawType();
        }
        if (type instanceof GenericArrayType) {
            return Array.newInstance(g(((GenericArrayType) type).getGenericComponentType()), 0).getClass();
        }
        if (type instanceof TypeVariable) {
            return Object.class;
        }
        if (type instanceof WildcardType) {
            return g(((WildcardType) type).getUpperBounds()[0]);
        }
        String className = type == null ? BuildConfig.TRAVIS : type.getClass().getName();
        throw new IllegalArgumentException("Expected a Class, ParameterizedType, or GenericArrayType, but <" + type + "> is of type " + className);
    }

    public static Type c(Type context, Class<?> contextRawType) {
        Type collectionType = h(context, contextRawType, Collection.class);
        if (collectionType instanceof WildcardType) {
            collectionType = ((WildcardType) collectionType).getUpperBounds()[0];
        }
        if (collectionType instanceof ParameterizedType) {
            return ((ParameterizedType) collectionType).getActualTypeArguments()[0];
        }
        return Object.class;
    }

    public static boolean d(@Nullable Type a, @Nullable Type b) {
        Type[] aTypeArguments;
        Type[] bTypeArguments;
        if (a == b) {
            return true;
        }
        if (a instanceof Class) {
            if (b instanceof GenericArrayType) {
                return d(((Class) a).getComponentType(), ((GenericArrayType) b).getGenericComponentType());
            }
            return a.equals(b);
        } else if (a instanceof ParameterizedType) {
            if (!(b instanceof ParameterizedType)) {
                return false;
            }
            ParameterizedType pa = (ParameterizedType) a;
            ParameterizedType pb = (ParameterizedType) b;
            if (pa instanceof b.C0206b) {
                aTypeArguments = ((b.C0206b) pa).f;
            } else {
                aTypeArguments = pa.getActualTypeArguments();
            }
            if (pb instanceof b.C0206b) {
                bTypeArguments = ((b.C0206b) pb).f;
            } else {
                bTypeArguments = pb.getActualTypeArguments();
            }
            if (!d(pa.getOwnerType(), pb.getOwnerType()) || !pa.getRawType().equals(pb.getRawType()) || !Arrays.equals(aTypeArguments, bTypeArguments)) {
                return false;
            }
            return true;
        } else if (a instanceof GenericArrayType) {
            if (b instanceof Class) {
                return d(((Class) b).getComponentType(), ((GenericArrayType) a).getGenericComponentType());
            }
            if (!(b instanceof GenericArrayType)) {
                return false;
            }
            return d(((GenericArrayType) a).getGenericComponentType(), ((GenericArrayType) b).getGenericComponentType());
        } else if (a instanceof WildcardType) {
            if (!(b instanceof WildcardType)) {
                return false;
            }
            WildcardType wa = (WildcardType) a;
            WildcardType wb = (WildcardType) b;
            if (!Arrays.equals(wa.getUpperBounds(), wb.getUpperBounds()) || !Arrays.equals(wa.getLowerBounds(), wb.getLowerBounds())) {
                return false;
            }
            return true;
        } else if (!(a instanceof TypeVariable) || !(b instanceof TypeVariable)) {
            return false;
        } else {
            TypeVariable<?> va = (TypeVariable) a;
            TypeVariable<?> vb = (TypeVariable) b;
            if (va.getGenericDeclaration() != vb.getGenericDeclaration() || !va.getName().equals(vb.getName())) {
                return false;
            }
            return true;
        }
    }

    static Type[] i(Type context, Class<?> contextRawType) {
        Class<Object> cls = Object.class;
        Class<String> cls2 = String.class;
        if (context == Properties.class) {
            return new Type[]{cls2, cls2};
        }
        Type mapType = h(context, contextRawType, Map.class);
        if (mapType instanceof ParameterizedType) {
            return ((ParameterizedType) mapType).getActualTypeArguments();
        }
        return new Type[]{cls, cls};
    }

    static Type h(Type context, Class<?> contextRawType, Class<?> supertype) {
        if (supertype.isAssignableFrom(contextRawType)) {
            return b.n(context, contextRawType, b.e(context, contextRawType, supertype));
        }
        throw new IllegalArgumentException();
    }

    static Type f(Type type) {
        Class<?> rawType = g(type);
        return b.n(type, rawType, rawType.getGenericSuperclass());
    }

    static Type a(Type type) {
        if (type instanceof GenericArrayType) {
            return ((GenericArrayType) type).getGenericComponentType();
        }
        if (type instanceof Class) {
            return ((Class) type).getComponentType();
        }
        return null;
    }
}
