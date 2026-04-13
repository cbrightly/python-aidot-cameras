package com.alibaba.fastjson.serializer;

import com.alibaba.fastjson.util.TypeUtils;
import com.google.maps.android.BuildConfig;
import io.netty.util.internal.StringUtil;
import java.lang.reflect.Type;
import java.util.List;

public final class ListSerializer implements ObjectSerializer {
    public static final ListSerializer instance = new ListSerializer();

    public final void write(JSONSerializer serializer, Object object, Object fieldName, Type fieldType, int features) {
        Type elementType;
        SerialContext context;
        Object item;
        char c;
        SerializeWriter out;
        char c2;
        List<?> list;
        JSONSerializer jSONSerializer = serializer;
        Object obj = object;
        int i = features;
        SerializeWriter serializeWriter = jSONSerializer.out;
        SerializerFeature serializerFeature = SerializerFeature.WriteClassName;
        boolean writeClassName = serializeWriter.isEnabled(serializerFeature) || SerializerFeature.isEnabled(i, serializerFeature);
        SerializeWriter out2 = jSONSerializer.out;
        if (writeClassName) {
            elementType = TypeUtils.getCollectionItemType(fieldType);
        } else {
            elementType = null;
        }
        if (obj == null) {
            out2.writeNull(SerializerFeature.WriteNullListAsEmpty);
            return;
        }
        List<?> list2 = (List) obj;
        if (list2.size() == 0) {
            out2.append((CharSequence) "[]");
            return;
        }
        SerialContext context2 = jSONSerializer.context;
        jSONSerializer.setContext(context2, obj, fieldName, 0);
        try {
            boolean isEnabled = out2.isEnabled(SerializerFeature.PrettyFormat);
            char c3 = StringUtil.COMMA;
            char c4 = ']';
            if (isEnabled) {
                out2.append('[');
                serializer.incrementIndent();
                int i2 = 0;
                for (Object next : list2) {
                    if (i2 != 0) {
                        try {
                            out2.append(c3);
                        } catch (Throwable th) {
                            th = th;
                            context = context2;
                            List<?> list3 = list2;
                            SerializeWriter serializeWriter2 = out2;
                        }
                    }
                    serializer.println();
                    if (next == null) {
                        c2 = c3;
                        context = context2;
                        list = list2;
                        Object obj2 = next;
                        c = c4;
                        out = out2;
                        jSONSerializer.out.writeNull();
                    } else if (jSONSerializer.containsReference(next)) {
                        jSONSerializer.writeReference(next);
                        c2 = c3;
                        context = context2;
                        list = list2;
                        Object obj3 = next;
                        c = c4;
                        out = out2;
                    } else {
                        ObjectSerializer itemSerializer = jSONSerializer.getObjectWriter(next.getClass());
                        Object item2 = next;
                        c = c4;
                        jSONSerializer.context = new SerialContext(context2, object, fieldName, 0, 0);
                        c2 = c3;
                        context = context2;
                        list = list2;
                        out = out2;
                        try {
                            itemSerializer.write(serializer, item2, Integer.valueOf(i2), elementType, features);
                        } catch (Throwable th2) {
                            th = th2;
                            jSONSerializer.context = context;
                            throw th;
                        }
                    }
                    i2++;
                    Object obj4 = fieldName;
                    list2 = list;
                    c3 = c2;
                    context2 = context;
                    out2 = out;
                    c4 = c;
                    Object obj5 = object;
                }
                SerialContext context3 = context2;
                List<?> list4 = list2;
                serializer.decrementIdent();
                serializer.println();
                out2.append(c4);
                jSONSerializer.context = context3;
                return;
            }
            char c5 = ',';
            context = context2;
            List<?> list5 = list2;
            SerializeWriter out3 = out2;
            out3.append('[');
            int i3 = 0;
            int size = list5.size();
            ObjectSerializer itemSerializer2 = null;
            while (i3 < size) {
                try {
                    Object obj6 = list5.get(i3);
                    if (i3 != 0) {
                        out3.append(c5);
                    }
                    if (obj6 == null) {
                        out3.append((CharSequence) BuildConfig.TRAVIS);
                    } else {
                        Class<?> clazz = obj6.getClass();
                        if (clazz == Integer.class) {
                            out3.writeInt(((Integer) obj6).intValue());
                        } else if (clazz == Long.class) {
                            long val = ((Long) obj6).longValue();
                            if (writeClassName) {
                                out3.writeLong(val);
                                out3.write(76);
                            } else {
                                out3.writeLong(val);
                            }
                        } else if ((SerializerFeature.DisableCircularReferenceDetect.mask & i) != 0) {
                            ObjectSerializer itemSerializer3 = jSONSerializer.getObjectWriter(obj6.getClass());
                            Class<?> cls = clazz;
                            Object obj7 = obj6;
                            itemSerializer3.write(serializer, obj6, Integer.valueOf(i3), elementType, features);
                            itemSerializer2 = itemSerializer3;
                        } else {
                            if (!out3.disableCircularReferenceDetect) {
                                item = obj6;
                                jSONSerializer.context = new SerialContext(context, object, fieldName, 0, 0);
                            } else {
                                item = obj6;
                            }
                            if (jSONSerializer.containsReference(item)) {
                                jSONSerializer.writeReference(item);
                            } else {
                                itemSerializer2 = jSONSerializer.getObjectWriter(item.getClass());
                                if ((SerializerFeature.WriteClassName.mask & i) == 0 || !(itemSerializer2 instanceof JavaBeanSerializer)) {
                                    itemSerializer2.write(serializer, item, Integer.valueOf(i3), elementType, features);
                                } else {
                                    ((JavaBeanSerializer) itemSerializer2).writeNoneASM(serializer, item, Integer.valueOf(i3), elementType, features);
                                }
                            }
                        }
                    }
                    i3++;
                    c5 = StringUtil.COMMA;
                } catch (Throwable th3) {
                    th = th3;
                    ObjectSerializer objectSerializer = itemSerializer2;
                    jSONSerializer.context = context;
                    throw th;
                }
            }
            out3.append(']');
            jSONSerializer.context = context;
        } catch (Throwable th4) {
            th = th4;
            context = context2;
            List<?> list6 = list2;
            SerializeWriter serializeWriter3 = out2;
            jSONSerializer.context = context;
            throw th;
        }
    }
}
