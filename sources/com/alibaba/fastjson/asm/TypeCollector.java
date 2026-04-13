package com.alibaba.fastjson.asm;

import androidx.exifinterface.media.ExifInterface;
import com.alibaba.fastjson.annotation.JSONType;
import com.alibaba.fastjson.util.ASMUtils;
import com.meituan.robust.Constants;
import java.lang.reflect.Modifier;
import java.util.HashMap;
import java.util.Map;

public class TypeCollector {
    private static String JSONType = ASMUtils.desc((Class<?>) JSONType.class);
    private static final Map<String, String> primitives = new HashMap<String, String>() {
        {
            put(Constants.INT, "I");
            put("boolean", "Z");
            put(Constants.BYTE, "B");
            put(Constants.CHAR, "C");
            put(Constants.SHORT, ExifInterface.LATITUDE_SOUTH);
            put("float", "F");
            put(Constants.LONG, "J");
            put(Constants.DOUBLE, "D");
        }
    };
    protected MethodCollector collector = null;
    protected boolean jsonType;
    private final String methodName;
    private final Class<?>[] parameterTypes;

    public TypeCollector(String methodName2, Class<?>[] parameterTypes2) {
        this.methodName = methodName2;
        this.parameterTypes = parameterTypes2;
    }

    /* access modifiers changed from: protected */
    public MethodCollector visitMethod(int access, String name, String desc) {
        if (this.collector != null || !name.equals(this.methodName)) {
            return null;
        }
        Type[] argTypes = Type.getArgumentTypes(desc);
        int longOrDoubleQuantity = 0;
        for (Type t : argTypes) {
            String className = t.getClassName();
            if (className.equals(Constants.LONG) || className.equals(Constants.DOUBLE)) {
                longOrDoubleQuantity++;
            }
        }
        if (argTypes.length != this.parameterTypes.length) {
            return null;
        }
        for (int i = 0; i < argTypes.length; i++) {
            if (!correctTypeName(argTypes[i], this.parameterTypes[i].getName())) {
                return null;
            }
        }
        MethodCollector methodCollector = new MethodCollector(Modifier.isStatic(access) ^ true ? 1 : 0, argTypes.length + longOrDoubleQuantity);
        this.collector = methodCollector;
        return methodCollector;
    }

    public void visitAnnotation(String desc) {
        if (JSONType.equals(desc)) {
            this.jsonType = true;
        }
    }

    private boolean correctTypeName(Type type, String paramTypeName) {
        String s = type.getClassName();
        StringBuilder braces = new StringBuilder();
        while (s.endsWith("[]")) {
            braces.append('[');
            s = s.substring(0, s.length() - 2);
        }
        if (braces.length() != 0) {
            Map<String, String> map = primitives;
            if (map.containsKey(s)) {
                braces.append(map.get(s));
                s = braces.toString();
            } else {
                braces.append(Constants.OBJECT_TYPE);
                braces.append(s);
                braces.append(';');
                s = braces.toString();
            }
        }
        return s.equals(paramTypeName);
    }

    public String[] getParameterNamesForMethod() {
        MethodCollector methodCollector = this.collector;
        if (methodCollector == null || !methodCollector.debugInfoPresent) {
            return new String[0];
        }
        return methodCollector.getResult().split(",");
    }

    public boolean matched() {
        return this.collector != null;
    }

    public boolean hasJsonType() {
        return this.jsonType;
    }
}
