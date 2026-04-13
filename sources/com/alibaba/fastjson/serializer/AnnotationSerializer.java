package com.alibaba.fastjson.serializer;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.util.Map;

public class AnnotationSerializer implements ObjectSerializer {
    public static AnnotationSerializer instance = new AnnotationSerializer();
    private static volatile Class sun_AnnotationType = null;
    private static volatile boolean sun_AnnotationType_error = false;
    private static volatile Method sun_AnnotationType_getInstance = null;
    private static volatile Method sun_AnnotationType_members = null;

    public void write(JSONSerializer serializer, Object object, Object fieldName, Type fieldType, int features) {
        Class[] interfaces = object.getClass().getInterfaces();
        if (interfaces.length != 1 || !interfaces[0].isAnnotation()) {
            JSONSerializer jSONSerializer = serializer;
            Object obj = object;
            return;
        }
        Class annotationClass = interfaces[0];
        if (sun_AnnotationType == null && !sun_AnnotationType_error) {
            try {
                sun_AnnotationType = Class.forName("sun.reflect.annotation.AnnotationType");
            } catch (Throwable ex) {
                sun_AnnotationType_error = true;
                throw new JSONException("not support Type Annotation.", ex);
            }
        }
        if (sun_AnnotationType != null) {
            if (sun_AnnotationType_getInstance == null && !sun_AnnotationType_error) {
                try {
                    sun_AnnotationType_getInstance = sun_AnnotationType.getMethod("getInstance", new Class[]{Class.class});
                } catch (Throwable ex2) {
                    sun_AnnotationType_error = true;
                    throw new JSONException("not support Type Annotation.", ex2);
                }
            }
            if (sun_AnnotationType_members == null && !sun_AnnotationType_error) {
                try {
                    sun_AnnotationType_members = sun_AnnotationType.getMethod("members", new Class[0]);
                } catch (Throwable ex3) {
                    sun_AnnotationType_error = true;
                    throw new JSONException("not support Type Annotation.", ex3);
                }
            }
            if (sun_AnnotationType_getInstance == null || sun_AnnotationType_error) {
                JSONSerializer jSONSerializer2 = serializer;
                Object obj2 = object;
                throw new JSONException("not support Type Annotation.");
            }
            try {
                try {
                    Map map = (Map) sun_AnnotationType_members.invoke(sun_AnnotationType_getInstance.invoke((Object) null, new Object[]{annotationClass}), new Object[0]);
                    JSONObject json = new JSONObject(map.size());
                    Object val = null;
                    for (Map.Entry<String, Method> entry : map.entrySet()) {
                        try {
                            Object obj3 = object;
                            try {
                                val = entry.getValue().invoke(object, new Object[0]);
                            } catch (IllegalAccessException | InvocationTargetException e) {
                            }
                        } catch (IllegalAccessException e2) {
                            Object obj4 = object;
                        } catch (InvocationTargetException e3) {
                            Object obj5 = object;
                        }
                        json.put(entry.getKey(), JSON.toJSON(val));
                    }
                    Object obj6 = object;
                    JSONSerializer jSONSerializer3 = serializer;
                    serializer.write((Object) json);
                } catch (Throwable ex4) {
                    throw new JSONException("not support Type Annotation.", ex4);
                }
            } finally {
                JSONSerializer jSONSerializer4 = serializer;
                Object obj7 = object;
                sun_AnnotationType_error = true;
                JSONException jSONException = new JSONException("not support Type Annotation.", ex4);
            }
        } else {
            JSONSerializer jSONSerializer5 = serializer;
            Object obj8 = object;
            throw new JSONException("not support Type Annotation.");
        }
    }
}
