package com.alibaba.fastjson.serializer;

import io.netty.util.internal.StringUtil;

public abstract class BeforeFilter implements SerializeFilter {
    private static final Character COMMA = Character.valueOf(StringUtil.COMMA);
    private static final ThreadLocal<Character> seperatorLocal = new ThreadLocal<>();
    private static final ThreadLocal<JSONSerializer> serializerLocal = new ThreadLocal<>();

    public abstract void writeBefore(Object obj);

    /* access modifiers changed from: package-private */
    public final char writeBefore(JSONSerializer serializer, Object object, char seperator) {
        ThreadLocal<JSONSerializer> threadLocal = serializerLocal;
        threadLocal.set(serializer);
        ThreadLocal<Character> threadLocal2 = seperatorLocal;
        threadLocal2.set(Character.valueOf(seperator));
        writeBefore(object);
        threadLocal.set(threadLocal.get());
        return threadLocal2.get().charValue();
    }

    /* access modifiers changed from: protected */
    public final void writeKeyValue(String key, Object value) {
        JSONSerializer serializer = serializerLocal.get();
        ThreadLocal<Character> threadLocal = seperatorLocal;
        char seperator = threadLocal.get().charValue();
        boolean ref = serializer.references.containsKey(value);
        serializer.writeKeyValue(seperator, key, value);
        if (!ref) {
            serializer.references.remove(value);
        }
        if (seperator != ',') {
            threadLocal.set(COMMA);
        }
    }
}
