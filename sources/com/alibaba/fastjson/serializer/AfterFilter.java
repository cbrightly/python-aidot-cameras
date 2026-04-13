package com.alibaba.fastjson.serializer;

import io.netty.util.internal.StringUtil;
import java.util.IdentityHashMap;

public abstract class AfterFilter implements SerializeFilter {
    private static final Character COMMA = Character.valueOf(StringUtil.COMMA);
    private static final ThreadLocal<Character> seperatorLocal = new ThreadLocal<>();
    private static final ThreadLocal<JSONSerializer> serializerLocal = new ThreadLocal<>();

    public abstract void writeAfter(Object obj);

    /* access modifiers changed from: package-private */
    public final char writeAfter(JSONSerializer serializer, Object object, char seperator) {
        ThreadLocal<JSONSerializer> threadLocal = serializerLocal;
        threadLocal.set(serializer);
        ThreadLocal<Character> threadLocal2 = seperatorLocal;
        threadLocal2.set(Character.valueOf(seperator));
        writeAfter(object);
        threadLocal.set(threadLocal.get());
        return threadLocal2.get().charValue();
    }

    /* access modifiers changed from: protected */
    public final void writeKeyValue(String key, Object value) {
        IdentityHashMap<Object, SerialContext> identityHashMap;
        JSONSerializer serializer = serializerLocal.get();
        ThreadLocal<Character> threadLocal = seperatorLocal;
        char seperator = threadLocal.get().charValue();
        boolean ref = serializer.containsReference(value);
        serializer.writeKeyValue(seperator, key, value);
        if (!ref && (identityHashMap = serializer.references) != null) {
            identityHashMap.remove(value);
        }
        if (seperator != ',') {
            threadLocal.set(COMMA);
        }
    }
}
