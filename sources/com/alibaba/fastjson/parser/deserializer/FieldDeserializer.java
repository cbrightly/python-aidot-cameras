package com.alibaba.fastjson.parser.deserializer;

import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.parser.DefaultJSONParser;
import com.alibaba.fastjson.serializer.BeanContext;
import com.alibaba.fastjson.util.FieldInfo;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.lang.reflect.Type;
import java.util.Collection;
import java.util.Collections;
import java.util.Map;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

public abstract class FieldDeserializer {
    protected BeanContext beanContext;
    protected final Class<?> clazz;
    public final FieldInfo fieldInfo;

    public abstract void parseField(DefaultJSONParser defaultJSONParser, Object obj, Type type, Map<String, Object> map);

    public FieldDeserializer(Class<?> clazz2, FieldInfo fieldInfo2) {
        this.clazz = clazz2;
        this.fieldInfo = fieldInfo2;
    }

    public Class<?> getOwnerClass() {
        return this.clazz;
    }

    public int getFastMatchToken() {
        return 0;
    }

    public void setValue(Object object, boolean value) {
        setValue(object, (Object) Boolean.valueOf(value));
    }

    public void setValue(Object object, int value) {
        setValue(object, (Object) Integer.valueOf(value));
    }

    public void setValue(Object object, long value) {
        setValue(object, (Object) Long.valueOf(value));
    }

    public void setValue(Object object, String value) {
        setValue(object, (Object) value);
    }

    public void setValue(Object object, Object value) {
        String str;
        if (value != null || !this.fieldInfo.fieldClass.isPrimitive()) {
            FieldInfo fieldInfo2 = this.fieldInfo;
            if (fieldInfo2.fieldClass == String.class && (str = fieldInfo2.format) != null && str.equals("trim")) {
                value = ((String) value).trim();
            }
            try {
                FieldInfo fieldInfo3 = this.fieldInfo;
                Method method = fieldInfo3.method;
                if (method == null) {
                    Field field = fieldInfo3.field;
                    if (fieldInfo3.getOnly) {
                        Class<?> cls = fieldInfo3.fieldClass;
                        if (cls == AtomicInteger.class) {
                            AtomicInteger atomic = (AtomicInteger) field.get(object);
                            if (atomic != null) {
                                atomic.set(((AtomicInteger) value).get());
                            }
                        } else if (cls == AtomicLong.class) {
                            AtomicLong atomic2 = (AtomicLong) field.get(object);
                            if (atomic2 != null) {
                                atomic2.set(((AtomicLong) value).get());
                            }
                        } else if (cls == AtomicBoolean.class) {
                            AtomicBoolean atomic3 = (AtomicBoolean) field.get(object);
                            if (atomic3 != null) {
                                atomic3.set(((AtomicBoolean) value).get());
                            }
                        } else if (Map.class.isAssignableFrom(cls)) {
                            Map map = (Map) field.get(object);
                            if (map != null) {
                                if (map == Collections.emptyMap()) {
                                    return;
                                }
                                if (!map.getClass().getName().startsWith("java.util.Collections$Unmodifiable")) {
                                    map.putAll((Map) value);
                                }
                            }
                        } else {
                            Collection collection = (Collection) field.get(object);
                            if (!(collection == null || value == null)) {
                                if (collection != Collections.emptySet() && collection != Collections.emptyList()) {
                                    if (!collection.getClass().getName().startsWith("java.util.Collections$Unmodifiable")) {
                                        collection.clear();
                                        collection.addAll((Collection) value);
                                    }
                                }
                            }
                        }
                    } else if (field != null) {
                        field.set(object, value);
                    }
                } else if (fieldInfo3.getOnly) {
                    Class<?> cls2 = fieldInfo3.fieldClass;
                    if (cls2 == AtomicInteger.class) {
                        AtomicInteger atomic4 = (AtomicInteger) method.invoke(object, new Object[0]);
                        if (atomic4 != null) {
                            atomic4.set(((AtomicInteger) value).get());
                        } else {
                            degradeValueAssignment(this.fieldInfo.field, method, object, value);
                        }
                    } else if (cls2 == AtomicLong.class) {
                        AtomicLong atomic5 = (AtomicLong) method.invoke(object, new Object[0]);
                        if (atomic5 != null) {
                            atomic5.set(((AtomicLong) value).get());
                        } else {
                            degradeValueAssignment(this.fieldInfo.field, method, object, value);
                        }
                    } else if (cls2 == AtomicBoolean.class) {
                        AtomicBoolean atomic6 = (AtomicBoolean) method.invoke(object, new Object[0]);
                        if (atomic6 != null) {
                            atomic6.set(((AtomicBoolean) value).get());
                        } else {
                            degradeValueAssignment(this.fieldInfo.field, method, object, value);
                        }
                    } else if (Map.class.isAssignableFrom(method.getReturnType())) {
                        try {
                            Map map2 = (Map) method.invoke(object, new Object[0]);
                            if (map2 != null) {
                                if (map2 != Collections.emptyMap()) {
                                    if (!map2.isEmpty() || !((Map) value).isEmpty()) {
                                        String mapClassName = map2.getClass().getName();
                                        if (!mapClassName.equals("java.util.ImmutableCollections$Map1") && !mapClassName.equals("java.util.ImmutableCollections$MapN")) {
                                            if (!mapClassName.startsWith("java.util.Collections$Unmodifiable")) {
                                                if (map2.getClass().getName().equals("kotlin.collections.b0")) {
                                                    degradeValueAssignment(this.fieldInfo.field, method, object, value);
                                                    return;
                                                }
                                                map2.putAll((Map) value);
                                            }
                                        }
                                    }
                                }
                            } else if (value != null) {
                                degradeValueAssignment(this.fieldInfo.field, method, object, value);
                            }
                        } catch (InvocationTargetException e) {
                            degradeValueAssignment(this.fieldInfo.field, method, object, value);
                        }
                    } else {
                        try {
                            Collection collection2 = (Collection) method.invoke(object, new Object[0]);
                            if (collection2 != null && value != null) {
                                String collectionClassName = collection2.getClass().getName();
                                if (collection2 != Collections.emptySet() && collection2 != Collections.emptyList() && collectionClassName != "java.util.ImmutableCollections$ListN" && collectionClassName != "java.util.ImmutableCollections$List12") {
                                    if (!collectionClassName.startsWith("java.util.Collections$Unmodifiable")) {
                                        if (!collection2.isEmpty()) {
                                            collection2.clear();
                                        } else if (((Collection) value).isEmpty()) {
                                            return;
                                        }
                                        if (!collectionClassName.equals("kotlin.collections.EmptyList")) {
                                            if (!collectionClassName.equals("kotlin.collections.EmptySet")) {
                                                collection2.addAll((Collection) value);
                                            }
                                        }
                                        degradeValueAssignment(this.fieldInfo.field, method, object, value);
                                    }
                                }
                            } else if (collection2 == null && value != null) {
                                degradeValueAssignment(this.fieldInfo.field, method, object, value);
                            }
                        } catch (InvocationTargetException e2) {
                            degradeValueAssignment(this.fieldInfo.field, method, object, value);
                        }
                    }
                } else {
                    method.invoke(object, new Object[]{value});
                }
            } catch (Exception e3) {
                throw new JSONException("set property error, " + this.clazz.getName() + "#" + this.fieldInfo.name, e3);
            }
        }
    }

    private static void degradeValueAssignment(Field field, Method getMethod, Object object, Object value) {
        if (!setFieldValue(field, object, value)) {
            Class<?> cls = object.getClass();
            cls.getDeclaredMethod("set" + getMethod.getName().substring(3), new Class[]{getMethod.getReturnType()}).invoke(object, new Object[]{value});
        }
    }

    private static boolean setFieldValue(Field field, Object object, Object value) {
        if (field == null || Modifier.isFinal(field.getModifiers())) {
            return false;
        }
        field.set(object, value);
        return true;
    }

    public void setWrappedValue(String key, Object value) {
        throw new JSONException("TODO");
    }
}
